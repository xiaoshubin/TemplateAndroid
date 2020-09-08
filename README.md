
![TemplateAndroid](https://github.com/xiaoshubin/TemplateAndroid/blob/master/pic/android_bee.jpg)

# TemplateAndroid

Android模板，主要引入两个libary,通用工具类[SmallUtils](https://github.com/xiaoshubin/SmallUtils)，封装的Retrofit网络工具类[SmallOkHttp](https://github.com/xiaoshubin/SmallOkHttp)

#### 引入的库

1. 集成了[dagger2](https://github.com/google/dagger)来注入网络请求层到Activity和Fragment基类，注入相关主要在包[inject](https://github.com/xiaoshubin/TemplateAndroid/tree/master/app/src/main/java/com/xqd/catplay/inject)里面
2. 开启了dataBinding，用来替代[butterknife](https://github.com/JakeWharton/butterknife) ，配合[Databinding Support](https://plugins.jetbrains.com/plugin/9271-databinding-support/)插件快熟构建xml中的dataBinding结构体
3. 引入[Apollo](https://github.com/Sloaix/Apollo)事件通知，基于Rxjava,类似EventBus
4. 引入[banner](https://github.com/youth5201314/banner)轮播图
5. 引入 [glide](https://github.com/bumptech/glide)图片加载，大家都在用
6. 引入RecyclerView适配器[BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)，大家都说好
7. 引入[XPopup](https://github.com/li-xiaojun/XPopup),popupWindow必备，且自定义很强
8. 引入[MagicIndicator](https://github.com/hackware1993/MagicIndicator)，如果TabLayout不能满足你，就用它吧，自定义很强
9. 引入[XXPermissions](https://github.com/getActivity/XXPermissions)权限管理，6.0以上的手机必备
10. 引入鲁班压缩[Luban](https://github.com/Curzibn/Luban)，压缩图片必备
11. 引入json解析[fastjson](https://github.com/alibaba/fastjson)，号称解析最快
12.  引入[greenDAO](https://github.com/greenrobot/greenDAO)数据库，占用内存少，注释建表
13. 引入[ImmersionBar](https://github.com/gyf-dev/ImmersionBar)沉浸式，快速设置导航栏和状态的显示隐藏和颜色
14. 引入[zxing](https://github.com/zxing/zxing)，二维码生成和扫描

#### 重要一：首先需要配置你本地local.properties的打包密钥配置，不然项目构建会报错
```
keystore.path =D\:\\mykey.jks
keystore.password = 123456
keystore.alias = myalias
keystore.alias_password = 123456
```

#### 重要二：修改了项目文件目录名称，不是包名.

例如现在文件层级名称为：

```
com.smallcake.template.databinding.ActivityMainBinding;
```

- 现象1：其中`com.smallcake.template`是文件目录的名称，我们修改此文件目录名称为`com.xiao.temp`后。

databinding生成的对应`ActivityMainBinding`路径为`com.xiao.temp.databinding.ActivityMainBinding;`

但是在Activity中引入的还是`com.smallcake.template.databinding.ActivityMainBinding;`且不爆错。

但运行就会出现错误提示：说找不到databinding对应文件。

- 解决1：修改Activity中的相应引入为`com.xiao.temp.databinding.ActivityMainBinding;`这个时候爆红也不用管，再次运行就可以了。如果红没有消失，请重启AndroidStudio.

- 现象2：如果改了文件目录名称的同时，开启了混淆。那么注意运行会出错`java.lang.ClassCastException: $Proxy0 cannot be cast to c.j.a.f.a`

- 解决2：需要修改对应混淆的api目录下的接口

  ```
  # 网络请求接口要排除混淆
  -keep class com.xiao.temp.api.**{ *;}
  ```

#### 做了哪些初始化配置

1.配置了[network_security_config.xml](https://github.com/xiaoshubin/TemplateAndroid/blob/master/app/src/main/res/xml/network_security_config.xml),避免Anroid9+的机器无法访问http接口，只能访问https

2.设置全面屏匹配样式，默认去掉title. `<item name="windowNoTitle">true</item>`

3.配置了[filepath_data.xml](https://github.com/xiaoshubin/TemplateAndroid/blob/master/app/src/main/res/xml/filepath_data.xml),Anroid7+获取文件要以FileProvider的方式获取

4.在MyApplication中初始化了工具类SmallUtils，网络工具类RetrofitHttp，事件通知Apollo，配置了多索引MultiDex（也就是总方法数超过限制>65536）

5.在build.gradle中配置了签名文件，所以需要我们配置本地`local.properties`四个参数，请看**重要一**。

配置了正式包和测试版都使用相同的签名文件，避免我们打包和刷机包冲突。配置了使用java8,开启了dataBinding的开关。自定义了打包文件名称，以（包名的最后一段+时间+v版本名称+打包方式.apk）来命名。

6.在app/proguard-rules.pro配置了混淆规则，但本模板并未开启混淆，需要的可以设置混淆为true.但是网络请求接口和实现需要排除混淆。

7.BaseFragment实现了懒加载机制，配合ViewPager来使用，需要设置viewPager.setOffscreenPageLimit(fragmnets.size);来实现懒加载机制，否则onBindView重复执行而onLazyLoad只执行一次，导致onLazyLoad中的操作无法再次执行的情况

8.加入屏幕适配方案具体参考：[一种非常好用的Android屏幕适配](https://www.jianshu.com/p/1302ad5a4b04)

#### 除了SmallUtils里面的工具类，其他的工具类

1.Banner工具类[BannerUtils](https://github.com/xiaoshubin/TemplateAndroid/blob/master/app/src/main/java/com/smallcake/template/utils/BannerUtils.java)，快速构建Banner通用轮播图

2.TabLayout工具类[TabUtils](https://github.com/xiaoshubin/TemplateAndroid/blob/master/app/src/main/java/com/smallcake/template/utils/TabUtils.java),用来代替系统自带TabLayout无法满足的情况。

3.[BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)升级为3.0.4后自带BaseDataBindingHolder，BaseQuickAdapter适配器中方便使用DataBind，在创建BaseQuickAdapter时候把BaseViewHolder替换为BaseDataBindingHolder，然后在`convert`方法中通过`helper.getDataBinding()`获取相对应的ViewDataBinding具体类后，就可以进行数据绑定了。具体可以查看[AdBeanAdapter](https://github.com/xiaoshubin/TemplateAndroid/blob/master/app/src/main/java/com/smallcake/template/adapter/AdBeanAdapter.java)

4.[DataBindingAdapter](https://github.com/xiaoshubin/TemplateAndroid/blob/master/app/src/main/java/com/smallcake/template/utils/DataBindingAdapter.java)，xml快速使用DataBind适配器工具类

5，[ZxingUtils](https://github.com/xiaoshubin/TemplateAndroid/blob/master/app/src/main/java/com/smallcake/template/utils/ZxingUtils.java),二维码生成器

6，[LocationUtils](https://github.com/xiaoshubin/TemplateAndroid/blob/master/app/src/main/java/com/smallcake/template/utils/LocationUtils.java),定位工具类，采用安卓系统定位获取经纬度，以及通过传入的位置信息获取经纬度

7，[CirclePercentView](https://github.com/xiaoshubin/TemplateAndroid/blob/master/SmallUtils/src/main/java/com/smallcake/utils/custom/CirclePercentView.java),圆形进度条



#### SmallUtils里面的工具类

1.新增可以自定义xml的雷达自定义RadarView

2.新增双波纹自定义视图[DynamicWave](https://github.com/xiaoshubin/TemplateAndroid/tree/master/SmallUtils/src/main/java/com/smallcake/utils/custom/DynamicWave.java)



