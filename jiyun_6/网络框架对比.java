//   volley
//        Volley 的中文翻译为“齐射、并发”
//        优点：
//        （1）自动调度网络请求 高并发网络连接；
//        （3）通过标准的 HTTP cache coherence（高速缓存一致性）缓存磁盘和内存透明的响应；
//        （4）支持指定请求的优先级（ 请求队列的优先级排序）；
//        （6）框架容易被定制，例如，定制重试或者回退功能；
//        （7）包含了调试与追踪工具；
//        （8）默认 Android2.3 及以上基于 HttpURLConnection，2.3 以下使用基于 HttpClient
//        （9）提供简便的图片加载工具
//        缺点：
//        （1）不能下载文件：这也是它最致命的地方

//  Android-async-http

//         Android-async-http 是一个强大的网络请求库，这个网络请求库是基于 Apache HttpClient 库之上的一个异步网络请求处理库。
// 可惜的是 Android 6.0 (api 23) SDK，不再提供 org.apache.http.* (只保留几个类)。
//        优点：
//        (1) 在匿名回调中处理请求结果
//        (2) 在 UI 线程外进行 http 请求
//        (3) 文件断点上传
//        (4) 智能重试
//        (5) 默认 gzip 压缩
//        (6) 支持解析成 Json 格式  pro  buff
//        (7) 可将 Cookies 持久化到 SharedPreference
//
//Afinal框架
//
//        Afinal主要是分四个模块：
//        (1) 数据库模块：android 中的 orm 框架，使用了线程池对 sqlite 进行操作。
//        (2) 注解模块：android 中的 ioc 框架，完全注解方式就可以进行UI绑定和事件绑定。无需 findViewById 和 setClickListener 等。其实它后面是使用反射来进行初始化的。
//        (3) 网络模块：通过 httpclient 进行封装 http 数据请求，支持 ajax方式加载，支持下载、上传文件功能。
//        (4) 图片缓存模块：通过 FinalBitmap，imageview 加载 bitmap 的时候无需考虑 bitmap 加载过程中出现的 oom 和 android 容器快速滑动时候出现的图片错位等现象。
//
//xUtils
//        跟Afinal是同类型的框架， 现在作者已经两三年前就没有进行更新了。
//        官网的简介：
//        xUtils3 api 变化较多, 已转至 https://github.com/wyouflf/xUtils3
//        xUtils 2.x 对 Android 6.0兼容不是很好, 请尽快升级至 xUtils3.
//        xUtils 包含了很多实用的android工具。
//        xUtils 支持大文件上传，更全面的http请求协议支持(10种谓词)，拥有更加灵活的 ORM，更多的事件注解支持且不受混淆影响...
//        xUitls 最低兼容android 2.2 (api level 8)
//  OkHttp3
//          1.处理网络请求的开源项目
//         2.由移动支付公司Square贡献
//         3.用于替代HttpUrlConnection和Apache HttpClient
//         4.OkHttp支持Android 2.3及其以上版本，JDK1.7以上
//         1.支持HTTP2/SPDY
//         2.socket自动选择最好路线，并支持自动重连
//         3.拥有自动维护的socket连接池，减少握手次数
//         4.拥有队列线程池，轻松写并发
//         5.拥有Interceptors轻松处理请求和响应（比如透明GZIP压缩，LOGGING）
//         6.基于Headers的缓存策略
//
//  retrofit
//        其实 retrofit 是根据 OKHttp 封装的框架，它的底层网络请求就是使用OKHttp的
//
//        优点：
//        （1）支持 okhttp
//        （2）注解处理，简化代码
//        （3）支持上传和下载文件
//        （4）支持自己更换解析方式
//        （5）支持多种http请求库
//  okgo

