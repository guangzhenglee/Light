[TOC]

#### 关于用`#{}` 和 `${}`

`${}` 也可以对传递进来的参数**原样拼接**在 SQL 中。代码如下：

```
<select id="getSubject3" parameterType="Integer" resultType="Subject">
    SELECT * FROM subject
    WHERE id = ${id}
</select>
```

- 实际场景下，不推荐这么做。因为，可能有 SQL 注入的风险。

`#{}` 是 SQL 的参数占位符，Mybatis 会将 SQL 中的 `#{}` 替换为 `?` 号，在 SQL 执行前会使用 PreparedStatement 的参数设置方法，按序给 SQL 的 `?` 号占位符设置参数值，比如 `ps.setInt(0, parameterValue)` 。 所以，`#{}` 是**预编译处理**，可以有效防止 SQL 注入，提高系统安全性

PreparedStatement会对SQL进行了预编译，在第一次执行SQL前数据库会进行分析、编译和优化，同时执行计划同样会被缓存起来，它允许数据库做参数化查询。在使用参数化查询的情况下，数据库不会将参数的内容视为SQL执行的一部分，而是作为一个字段的属性值来处理，这样就算参数中包含破环性语句（or ‘1=1’）也不会被执行。



#### 关于字段名称和表名不一样的问题

-----

#### 关于字段名称和表名不一样的问题

第一种， 通过在查询的 SQL 语句中定义字段名的别名，让字段名的别名和实体类的属性名一致。代码如下：

```
<select id="selectOrder" parameterType="Integer" resultType="Order"> 
    SELECT order_id AS id, order_no AS orderno, order_price AS price 
    FROM orders 
    WHERE order_id = #{id}
</select>
```

- 这里，艿艿还有几点建议：
  - 1、数据库的关键字，统一使用大写，例如：`SELECT`、`AS`、`FROM`、`WHERE` 。
  - 2、每 5 个查询字段换一行，保持整齐。
  - 3、`,` 的后面，和 `=` 的前后，需要有空格，更加清晰。
  - 4、`SELECT`、`FROM`、`WHERE` 等，单独一行，高端大气。

------

第二种，是第一种的特殊情况。大多数场景下，数据库字段名和实体类中的属性名差，主要是前者为**下划线风格**，后者为**驼峰风格**。在这种情况下，可以直接配置如下，实现自动的下划线转驼峰的功能。

```
<setting name="logImpl" value="LOG4J"/>
    <setting name="mapUnderscoreToCamelCase" value="true" />
</settings>
```

😈 也就说，约定大于配置。非常推荐！

------

第三种，通过 `<resultMap>` 来映射字段名和实体类属性名的一一对应的关系。代码如下：

```
<resultMap type="me.gacl.domain.Order" id=”OrderResultMap”> 
    <!–- 用 id 属性来映射主键字段 -–> 
    <id property="id" column="order_id"> 
    <!–- 用 result 属性来映射非主键字段，property 为实体类属性名，column 为数据表中的属性 -–> 
    <result property="orderNo" column ="order_no" /> 
    <result property="price" column="order_price" /> 
</resultMap>

<select id="getOrder" parameterType="Integer" resultMap="OrderResultMap">
    SELECT * 
    FROM orders 
    WHERE order_id = #{id}
</select>
```

- 此处 `SELECT *` 仅仅作为示例只用，实际场景下，千万千万千万不要这么干。用多少字段，查询多少字段。
- 

#### include怎么用

```
  <sql id="selectConfigVo">
      select config_id, config_name, config_key, config_value, config_type, create_by, create_time, update_by, update_time, remark 
from sys_config
  </sql>
```

可以在任何的地方使用下面的代表查询，就不用每个地方都写一遍。

`<include refid="selectConfigVo"/>`

也可以拼接其他的条件，如下面

```
  <sql id="selectConfigVo">
      select config_id, config_name, config_key, config_value, config_type, create_by, create_time, update_by, update_time, remark 
from sys_config
  </sql>

  <!-- 查询条件 -->
  <sql id="sqlwhereSearch">
      <where>
          <if test="configId !=null">
              and config_id = #{configId}
          </if>
          <if test="configKey !=null and configKey != ''">
              and config_key = #{configKey}
          </if>
      </where>
  </sql>

  <select id="selectConfig" parameterType="SysConfig" resultMap="SysConfigResult">
      <include refid="selectConfigVo"/>
      <include refid="sqlwhereSearch"/>
  </select>
```



#### 关于concat拼接

```
concat('%', #{configName}, '%')
```

可以拼接字段



#### 大于和小于判断

```
&gt;=大于
&lt;=小于
```



#### 实体类中map的用法

```
#{params.beginTime}
```

来获取`private transient Map<String, Object> params;`中的属性，不过要约定好



#### foreach的用法

下面表格是我总结的各个属性的用途和注意点。

foreach属性

| 属性       | **描述**                                                     |
| ---------- | ------------------------------------------------------------ |
| item       | 循环体中的具体对象。支持属性的点路径访问，如item.age,item.info.details。具体说明：在list和数组中是其中的对象，在map中是value。
该参数为必选。 |
| collection | 要做foreach的对象，作为入参时，List<?>对象默认用list代替作为键，数组对象有array代替作为键，Map对象没有默认的键。当然在作为入参时可以使用@Param("keyName")来设置键，设置keyName后，list,array将会失效。 除了入参这种情况外，还有一种作为参数对象的某个字段的时候。举个例子：
如果User有属性List ids。入参是User对象，那么这个collection = "ids"
如果User有属性Ids ids;其中Ids是个对象，Ids有个属性List id;入参是User对象，那么collection = "ids.id"
上面只是举例，具体collection等于什么，就看你想对那个元素做循环。
该参数为必选。 |
| separator  | 元素之间的分隔符，例如在in()的时候，separator=","会自动在元素中间用“,“隔开，避免手动输入逗号导致sql错误，如in(1,2,)这样。该参数可选 |
| open       | foreach代码的开始符号，一般是(和close=")"合用。常用在in(),values()时。该参数可选。 |
| close      | foreach代码的关闭符号，一般是)和open="("合用。常用在in(),values()时。该参数可选。 |
| index      | 在list和数组中,index是元素的序号，在map中，index是元素的key，该参数可选。 |



item	循环体中的具体对象。支持属性的点路径访问，如item.age,item.info.details。
具体说明：在list和数组中是其中的对象，在map中是value。
该参数为必选。
collection	要做foreach的对象，作为入参时，List<?>对象默认用list代替作为键，数组对象有array代替作为键，Map对象没有默认的键。
当然在作为入参时可以使用@Param("keyName")来设置键，设置keyName后，list,array将会失效。 除了入参这种情况外，还有一种作为参数对象的某个字段的时候。举个例子：
如果User有属性List ids。入参是User对象，那么这个collection = "ids"
如果User有属性Ids ids;其中Ids是个对象，Ids有个属性List id;入参是User对象，那么collection = "ids.id"
上面只是举例，具体collection等于什么，就看你想对那个元素做循环。
该参数为必选。
separator	元素之间的分隔符，例如在in()的时候，separator=","会自动在元素中间用“,“隔开，避免手动输入逗号导致sql错误，如in(1,2,)这样。该参数可选。
open	foreach代码的开始符号，一般是(和close=")"合用。常用在in(),values()时。该参数可选。
close	foreach代码的关闭符号，一般是)和open="("合用。常用在in(),values()时。该参数可选。
index	在list和数组中,index是元素的序号，在map中，index是元素的key，该参数可选。