一些用法

#### `ordinal()`方法来返回枚举类的序数

```
public enum BusinessStatus {
    /**
     * 成功
     */
    SUCCESS,

    /**
     * 失败
     */
    FAIL,
}
```

比如`operLog.setStatus(BusinessStatus.SUCCESS.ordinal());`返回的就是给状态为设置 `0`，因为`SUCCESS`在`BusinessStatus` 中是第一个

