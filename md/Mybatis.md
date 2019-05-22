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

**foreach属性**

| 属性       | 描述                                                         |
| ---------- | ------------------------------------------------------------ |
| item       | 循环体中的具体对象。支持属性的点路径访问，如item.age,item.info.details。具体说明：在list和数组中是其中的对象，在map中是value |
| collection | 要做foreach的对象，作为入参时，List<?>对象默认用list代替作为键，数组对象有array代替作为键，Map对象没有默认的键。当然在作为入参时可以使用@Param("keyName")来设置键，设置keyName后，list,array将会失效。 除了入参这种情况外，还有一种作为参数对象的某个字段的时候。举个例子：<br/>如果User有属性List ids。入参是User对象，那么这个collection = "ids"
如果User有属性Ids ids;其中Ids是个对象，Ids有个属性List id;入参是User对象，那么collection = "ids.id"
上面只是举例，具体collection等于什么，就看你想对那个元素做循环。
该参数为必选 |
| separator  | 元素之间的分隔符，例如在in()的时候，separator=","会自动在元素中间用“,“隔开，避免手动输入逗号导致sql错误，如in(1,2,)这样。该参数可选 |
| open       | foreach代码的开始符号，一般是(和close=")"合用。常用在in(),values()时。该参数可选 |
| close      | foreach代码的关闭符号，一般是)和open="("合用。常用在in(),values()时。该参数可选 |
| index      | 在list和数组中,index是元素的序号，在map中，index是元素的key，该参数可选 |



#### resultMap中association和collection

resultMap中collection属性(一对多关系结果集映射)和association属性(多对一关系结果集映射)

association – 映射到JavaBean 的某个“复杂类型”属性,其他JavaBean类

collection –复杂类型集合

举个例子

```
<resultMap type="com.light.system.domain.SysUser" id="SysUserResult">
    <id property="userId" column="user_id"/>
    <result property="deptId" column="dept_id"/>
    <result property="loginName" column="login_name"/>
    <result property="userName" column="user_name"/>
    <association property="dept" column="dept_id" javaType="com.light.system.domain.SysDept" resultMap="deptResult"/>
    <collection property="roles" javaType="java.util.List" resultMap="RoleResult"/>
</resultMap>

<resultMap id="deptResult" type="com.light.system.domain.SysDept">
    <id property="deptId" column="dept_id"/>
    <result property="parentId" column="parent_id"/>
    <result property="deptName" column="dept_name"/>
    <result property="orderNum" column="order_num"/>
    <result property="status" column="dept_status"/>
</resultMap>

<resultMap id="RoleResult" type="com.light.system.domain.SysRole">
    <id property="roleId" column="role_id"/>
    <result property="roleName" column="role_name"/>
    <result property="roleKey" column="role_key"/>
    <result property="roleSort" column="role_sort"/>
    <result property="dataScope" column="data_scope"/>
    <result property="status" column="role_status"/>
</resultMap>
```

一个用户对应多个角色，属于一对多。

但是多个用户都属于一个部门，属于多对一



#### mysql中FIND_IN_SET的使用方法

在[mysql](http://www.devdo.net/tag/mysql)中，有时我们在做数据库查询时，需要得到某字段中包含某个值的记录，但是它也不是用like能解决的，使用like可能查到我们不想要的记录，它比like更精准，这时候mysql的[FIND_IN_SET](http://www.devdo.net/tag/find_in_set)函数就派上用场了，下面来具体了解一下。

**FIND_IN_SET(str,strlist)函数**

str 要查询的字符串

strlist 字段名 参数以”,”分隔 如 (1,2,6,8)



#### mysql中replace into的作用

replace into 跟 insert 功能类似，不同点在于：replace into 首先尝试插入数据到表中， 1. 如果发现表中已经有此行数据（根据主键或者唯一索引判断）则先删除此行数据，然后插入新的数据。 2. 否则，直接插入新数据。



#### Mybatis中<!CDATA[[]]>的使用

<!CDATA[[]]>的意思是遇到 <= ,>=这些运算符按照原文本写入

```
WHERE o.last_access_time <![CDATA[ <= ]]> #{lastAccessTime}
```

意思是将 <= 转义，但是还不如用 `&lt;`来写