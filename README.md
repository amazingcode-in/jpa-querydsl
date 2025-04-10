# jpa-querydsl

## For Joins

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

## To fetch both at once

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