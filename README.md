#
# ORDER MANAGEMENT SYSTEM. 

In this project, we'll create a management system for orders. THese orders have customers and products. 

For the first time used static factory methods, very useful!

# BEST PRACTICES
In this file, we'll have the best practices learnt from this project. Which apply to this specific project and its requirements.  

# CUSTOMER HOLDS A LIST LONG
We do this, because Customer, Order and Product arde aggregates. We don't reference both directly but using their IDs; this way, we keep a loose coupling and also improve performance. 

# OrderLine not having a Product object. 
This class is a Value object, we're interested in having a snapshot of the products at a point in time. If the Product information changes (aggregate behavior), the information in OrderLine will also change. 

# ID and SKU in Product.
The SKU will be a UNIQUE field. This will help us find products in our DB while ID will be the "Identity" or PK for our Product to reference it from other tables. 

# DATE NOT IN THE PAST CONSTRAINT FOR ORDER
We can have a single constructor, but if we did that, our creation of new instances and reconstructing from DB will overlap and apply the same rules for both. We want to create new Objects that don't have the Date in the past, but when loading from the DB, we can have dates in the past. 

The solution is to use **static factory methods**. This way, we ensure that we have different entry points for different operations and so, we can validate specific rules in different cases. 

# DUPLICATED VALIDATION IN OrderLine.validatePrice and Product.validatePrice
We take the minor duplication since it's just one duplication. IN the case we had more duplications we could start thinking about a validator class for our Domain.

# CUSTOMERENTITY.ONETOMANY CASCADE AND ORPHANREMOVAL
**CASCADE**
We use cascade.ALL, we'll delete all the orders a customer made, we might not want that (depends on business), for using it, it's best to have the persist so all the children will be saved when the customer is also saved. 

**ORPHANREMOVAL**
WHen true, if an Order is deleted from the list of orders, we'll assume that order is no longer required and the DB will delete the entry instead of leaving it with a null customer_id. 

orphanRemoval = true is most appropriate when the child entity cannot meaningfully exist without its parent — for example, an AddressEntity that belongs to only one CustomerEntity

# BEAN VALIDATION ON ENTITIES
It's a good option but in some cases might be overengieering since the DTO's already check. IN this case, we're doing a simple api with not much evolution. Once created, it'll not evolve. In projects that probably will escalate, it's a good option to have validation on our entities and the DTOs to ensure certain constraints. 

# DIFFERENT OBJECTS REFERENCING THE SAME COLUMN -> OrderLineEntity

In this class, we have a composite PK given by the orderId and the productId. Besides that, for our relationships, we need to have the Object that references the other class and has to be referenced to the product_id/order_id columns. That causes a Mapping exception in JPA because both fields can't manage the same column. 

The solution is to keep the Objects referencing the other classes just readable. So the only one that concern to write in the column is the orderId and we just keep our relationship objects for reading. 

```
@Id
    @Column(name = "product_id")
    private Long productId;

    @Id
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private OrderEntity orderObject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private ProductEntity product; 

```

# REMOVAL OF ORDERLINE LIST IN PRODUCT ENTITY
The link lives in the intermediate table. 

```
 @OneToMany(mappedBy = "product",cascade = CascadeType.PERSIST)
    private List<OrderLineEntity> orderProducts = new ArrayList<>();
```
We remove this because a product doesn't need to know in which order it was in. We'll load all the orders unnecessarily. Our OrderLineEntity already maps to our product using the product_id column, it already has the relationship. 

Besides that, having that, our entities wouldn't match with our domani model. In the Product class we do'nt have a List of OrderLine so the Product mapper won't ever use that. 

# ORPHAN REMOVAL = TRUE IN ORDER ENTITY
OrderLine is a Value object added to Order.It has no meaning without an order, this class manages the lifecycle of orderLine. Product does not. We can delete a product but still keep the record. 

What the flag does is making sure that when an order is deleted, the children linked to that order are too. 

# REMOVING THE ORDER LIST IN CUSTOMER.JAVA

We had a List<OrderId> to hold the ids from the orders made by the Customer. For the object itself is not improtant to know which orders it has created, instead of it being a domain concern, is a repository concern; we can call our repository and bring the orders based on the customer id since our Order class has the customer_id field which helps modeling the relationship to the customer. 

By doing this, we also remove the issue of the addOrder method. The id of the order passed would be null and we'd save null ids in the list if we didn't save the Order first. 

# REMOVING THE ORDER LIST IN CUSTOMERENTITY.JAVA UNIDIRECTIONAL RELATIONSHIP 
It has kinda the same reasoning as above. First, it improves performance, the bidirectional relationships (thogh widely taught), are not strong for performance; our best shot is to, liike with a SQL query, keep the FK in one table referencing the parent table and that's it, our entities should model that. That way we avoid a lot of performance and memory issues. Besides that, our Customer class no longer needs a List of Orders so having it in the entity adds no value. 

Naturally the 1:M relationships are unidirectional. The orders table has the customer_id column (FK) to reference to the customers. customers doesn't know anything about orders. 

to get all the orders based on the customer id, we use the repository layer, we make it its responsibility. 

A unidirectional many-to-one JPA relationship is defined by placing the @ManyToOne annotation on the field in the "many" (child) side of the relationship, without any reference back to the child in the "one" (parent) entity. This is the most natural and efficient way to map a standard foreign key in a relational database, as the foreign key column resides in the child entity's table. 
Vlad Mihalcea



Since you are strictly separating layers, the flow looks like this:

Service receives a request: "Add Product ID 5 to Order ID 10."

Service asks ProductRepository for the ProductEntity.

Service (or a Mapper) takes the name, sku, and price from that ProductEntity.

Service creates a new Domain OrderLine with those values.

Service adds that OrderLine to the Domain Order.

Repository then maps that Domain Order back to an OrderEntity, which now has those snapshot fields filled.

# KEEPING ORDERLINEENTITY PRODUCTENTITY OBJECT AND ITS FIELDS
We're doing this to keep certain integrity, for reports in the case we want to have information on products and also, we have the Product fields to keeep a snapshot, in a way, we have best of both worlds. 