# jpa-querydsl

### Basic query
```java
List<Person> persons = queryFactory.selectFrom(person)
  .where(
    person.firstName.eq("John"),
    person.lastName.eq("Doe"))
  .fetch();
```

### Order
```java
List<Person> persons = queryFactory.selectFrom(person)
  .orderBy(person.lastName.asc(), 
           person.firstName.desc())
  .fetch();
```

### Subqueries
```java
List<Person> persons = queryFactory.selectFrom(person)
  .where(person.children.size().eq(
    JPAExpressions.select(parent.children.size().max())
                  .from(parent)))
  .fetch();
```

### Tuple projection
```java
List<Tuple> tuples = queryFactory.select(
    person.lastName, person.firstName, person.yearOfBirth)
  .from(person)
  .fetch();
```

### For Joins
```java
public Address getEmployeeAddress(Long employeeId) {
    JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

    QEmployee employee = QEmployee.employee;
    QAddress address = QAddress.address;

    return queryFactory.select(address)
        .from(employee)
        .join(employee.address, address)
        .where(employee.id.eq(employeeId))
        .fetchOne();
}
```

### To fetch both at once
```java
Tuple result = queryFactory
    .select(employee, address)
    .from(employee)
    .join(employee.address, address)
    .where(employee.id.eq(employeeId))
    .fetchOne();

Employee emp = result.get(employee);
Address addr = result.get(address);
```
