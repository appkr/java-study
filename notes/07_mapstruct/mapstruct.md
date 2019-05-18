## MapStruct

- https://www.youtube.com/watch?v=nvjqtWQ5zj8

```java
// Class Level
@Mapper(
    componentModel = "spring",
    uses = {FooMapper.class}
)
```
```java
// Method Level
@Mapping(
    target = "material.materialType",
    source = "material",
    dateFormat = "yyyy/MM/dd",
    numberFormat = "$#.00",
    expression = "java(firstName.getValue() + lastName.getValue)",
    constant = "NoIdeaInc",
    ignore = true
)
```
```java
@IterableMapping(dateFormat = "dd.MM.yyyy")
List<String> stringListToDateList(List<Date> dates);
```
```java
@MapMapping(valueDateFormat = "dd.MM.yyyy")
Map<String, String> longDateMapToStringStringMap(Map<Long, Date> source);
```
```java
@ValueMappings({
    @ValueMapping(source = "EXTRA", target = "SPECIAL"),
    @ValueMapping(source = "STANDARD", target = "DEFAULT"),
    @ValueMapping(source = "NORMAL", target = "DEFAULT"),
    @ValueMapping( source = MappingConstants.NULL, target = "DEFAULT" ),
    @ValueMapping( source = MappingConstants.ANY_REMAINING, target = "SPECIAL" )
})
ExternalOrderType orderTypeToExternalOrderType(OrderType orderType);
```
```java
// Update
@Mapper(uses = { DtoFactory.class, EntityFactory.class, CarMapper.class } )
public interface OwnerMapper {
    void updateOwnerDto(Owner owner, @MappingTarget OwnerDto ownerDto);
    void updateOwner(OwnerDto ownerDto, @MappingTarget Owner owner);
}
```
```java
imports java.util.UUID;

@Mapper( imports = UUID.class )
public interface SourceTargetMapper {
    @Mapping(target="id", source="sourceId", defaultExpression = "java( UUID.randomUUID().toString() )")
    Target sourceToTarget(Source s);
}
```
```java
// Decorator
@Mapper
@DecoratedWith(PersonMapperDecorator.class)
public interface PersonMapper {
    PersonDto personToPersonDto(Person person);
    AddressDto addressToAddressDto(Address address);
}

public abstract class PersonMapperDecorator implements PersonMapper {
    private final PersonMapper delegate;
    public PersonMapperDecorator(PersonMapper delegate) {
        this.delegate = delegate;
    }

    @Override
    public PersonDto personToPersonDto(Person person) {
        PersonDto dto = delegate.personToPersonDto( person );
        dto.setFullName( person.getFirstName() + " " + person.getLastName() );
        return dto;
    }
}
```
