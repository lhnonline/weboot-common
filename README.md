

## weboot-common
- 对restful接口返回做了包装

## @EnableHttpRequestLogAop
- 通过这个注解，打开对所有http请求的日志记录
- 通过实现com.github.lhnonline.boot.common.log.ILogWriter接口自定义日志的输出形式或者输出地点，比如数据库。

## @EnableHttpStatusToHeaderAop
- 通过这个注解，把返回结果中的状态码写进返回的http 状态码

## 依赖
- fastjson
- mybatis-plus-extension

## 默认配置文件

```yaml
weboot:
  log:
    ignore-param-types: org.springframework.validation.BeanPropertyBindingResult, #日志中不记录哪些类型的参数英文逗哈分割
    header-enabled: false # 日志中是否打印header中的参数，默认false
    header-all: false     # 是否打印全部的header中的参数，默认false
    header-names: host    # 打印哪些header中的参数，英文逗号分隔

```

## pom.xml
```xml
<dependency>
     <groupId>com.github.lhnonline</groupId>
     <artifactId>weboot-common</artifactId>
     <version>1.0.0</version>
</dependency>
```

## 接口包装对象

```java

// 通用返回对象
public class BaseResult<T> {
    /**
     * 状态码
     */
    protected Integer code;
    /**
     * 是否成功
     */
    protected Boolean success;
    /**
     * 成功消息，或者错误消息
     */
    protected String msg;
    /**
     * 成功时返回的数据
     */
    protected T data;
    /**
     * 错误时候返回的数据
     * <br/>
     * 如果提交表单时候，不想用lhnonline.github.online.common.util.FormResult返回错误信息，可以把错误信息放到error中
     */
    protected T error;
}

// 提交表单时返回对象
public class FormResult extends BaseResult {
    /**
     * 表单错误信息
     */
    protected FormErrors formErrors;
}

// 分页查询时返回对象
public class PageResult<T> extends BaseResult<T> {

    /**
     * 总记录条数
     */
    private Long total;
}

```
