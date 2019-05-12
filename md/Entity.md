#### 如何使用lombok还用父类的属性

当使用`@Data`注解时，则有了`@EqualsAndHashCode`注解，那么就会在此类中存在`equals(Object other)` 和 `hashCode()`方法，且不会使用父类的属性

修复此问题的方法很简单： 
1. 使用@Getter @Setter @ToString代替@Data并且自定义equals(Object other) 和 hashCode()方法，比如有些类只需要判断主键id是否相等即足矣。 
2. 或者使用在使用@Data时同时加上@EqualsAndHashCode(callSuper=true)注解。
---------------------
