##### 一、rabbitframework1.1.3版本说明:
	1、修改所有pom版本,升级rabbitframework版本号为1.1.3
	2、security框架：
        A、修改AbstractSecurityFilter类中filterUrl请求，匹配过滤条件将直接
        调用底层Filter的过滤方法,否则调用security的Filter。
        B、修改RabbitContextResource类,添加公共方法。
        C、新增freemarker模板扩展 类,通过web.xml中的配置对模板后缀名进行定义,
        默认为.ftl。

##### 二、rabbitframework2.0版本说明：
    1、修改所有pom版本,升级rabbitframework版本号为2.0,抽取父pom(完成)
    2､spring升级到4.3.9.RELEASE
    3､web框架：
      A、web框架中jeasey版本升级到2.25.1
    4､dbase框架：
        A、新增Mapper基类，默认实现增、删、改、查操作;
        B、新增批量更新、插入;
    5、security框架：
       A、优化改进;
	


