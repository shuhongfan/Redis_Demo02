### NoSQL特点

狂神说Redis笔记

## 狂神聊Redis

学习方式：不是为了面试和工作学习！仅仅是为了兴趣！兴趣才是最好的老师！ 基本的理论先学习，然后将知识融汇贯通！

##### 狂神的Redis课程安排：

nosql 讲解

阿里巴巴架构演进nosql 数据模型Nosql 四大分类CAP

BASE

Redis 入门

Redis安装（Window & Linux服务器） 五大基本数据类型

String List Set Hash Zset

三种特殊数据类型

geo hyperloglog bitmap

Redis 配置详解

Redis 持久化RDB AOF

Redis 事务操作

Redis 实现订阅发布

Redis 主从复制

Redis 哨兵模式（现在公司中所有的集群都用哨兵模式） 缓存穿透及解决方案

缓存击穿及解决方案缓存雪崩及解决方案基础API 之 Jedis 详解

SpringBoot 集成 Redis 操作

Redis 的实践分析

## Nosql概述

### 为什么要用Nosql

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=OTk5ZDYxOTMwMjhiNWFmZWFkZGMxNmFjOWZmY2ZkZTlfQ3ZyMnJpTmJCRlU1UTBLM1FmNlVWSm5FMXJSREtQcUtfVG9rZW46Ym94Y25SZHJWQ1NUbWl5cVU2ckFtR2psd3I4XzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

90年代，一个基本的网站访问量一般不会太大，单个数据库完全足够！ 那个时候，更多的去使用静态网页 Html ~ 服务器根本没有太大的压力！ 思考一下，这种情况下：整个网站的瓶颈是什么？

1、数据量如果太大、一个机器放不下了！

2、数据的索引 （B+ Tree），一个机器内存也放不下

3、访问量（读写混合），一个服务器承受不了~

只要你开始出现以上的三种情况之一，那么你就必须要晋级！

网站80%的情况都是在读，每次都要去查询数据库的话就十分的麻烦！所以说我们希望减轻数据的压 力，我们可以使用缓存来保证效率！

发展过程： 优化数据结构和索引--> 文件缓存（IO）---> Memcached（当时最热门的技术！）

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=NGUwMmQ3NDc3NTIwOTA3NjM3ZDI0YWJhMDYxNTcxMzRfNDhPTHJLV1hTa1VkVVE4UWdtazJRQndTWEx6MG9sdzBfVG9rZW46Ym94Y25KY1JzTThjemxCOFd4WG1oNEhBNGtoXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

技术和业务在发展的同时，对人的要求也越来越高！

本质：数据库（读，写）

早些年MyISAM： 表锁，十分影响效率！高并发下就会出现严重的锁问题转战Innodb：行锁

慢慢的就开始使用分库分表来解决写的压力！ MySQL 在哪个年代推出 了表分区！这个并没有多少公司使用！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=NDU2ZmEyYzcwMTA1NTQyMTc3Zjc5NWVmNWRjNDE2OGVfYkFxRkhFMklIdWFJNlg2SUp2dlh6OHVjOXFqdXdZNXVfVG9rZW46Ym94Y25BMEwwQ0t6Z2VlSjY5N1VLc1h2c1VmXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

MySQL 的 集群，很好满足哪个年代的所有需求！

2010--2020    十年之间，世界已经发生了翻天覆地的变化；（定位，也是一种数据，音乐，热榜！）

MySQL 等关系型数据库就不够用了！数据量很多，变化很快~！

MySQL 有的使用它来村粗一些比较大的文件，博客，图片！数据库表很大，效率就低了！如果有一种数据库来专门处理这种数据,

MySQL压力就变得十分小（研究如何处理这些问题！）大数据的IO压力下，表几乎没法更大！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=MzFhODM2NzMyODU2MzVjZjU4NGMxNjFiMmI2MjE2NTBfc2FMQ0RlTE1yemE3eHU5Z3cxdVVUR3RxbG1pZHIySXZfVG9rZW46Ym94Y256aENoQWNrblRSWlpYcWpjOVZJYmdyXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

用户的个人信息，社交网络，地理位置。用户自己产生的数据，用户日志等等爆发式增长！ 这时候我们就需要使用NoSQL数据库的，Nosql 可以很好的处理以上的情况！

### 什么是NoSQL

NoSQL = Not Only SQL （不仅仅是SQL） 关系型数据库：表格 ，行 ，列

泛指非关系型数据库的，随着web2.0互联网的诞生！传统的关系型数据库很难对付web2.0时代！尤其   是超大规模的高并发的社区！ 暴露出来很多难以克服的问题，NoSQL在当今大数据环境下发展的十分迅速，Redis是发展最快的，而且是我们当下必须要掌握的一个技术！

很多的数据类型用户的个人信息，社交网络，地理位置。这些数据类型的存储不需要一个固定的格式！ 不需要多月的操作就可以横向扩展的 ！ Map<String,Object> 使用键值对来控制！

解耦！

1、方便扩展（数据之间没有关系，很好扩展！）

2、大数据量高性能（Redis 一秒写8万次，读取11万，NoSQL的缓存记录级，是一种细粒度的缓存，性能会比较高！）

3、数据类型是多样型的！（不需要事先设计数据库！随取随用！如果是数据量十分大的表，很多人就无 法设计了！）

4、传统 RDBMS 和 NoSQL

大数据时代的3V：主要是描述问题的

1. 海量Volume
2. 多样Variety
3. 实时Velocity

大数据时代的3高：主要是对程序的要求

1. 高并发
2. 高可扩
3. 高性能

真正在公司中的实践：NoSQL + RDBMS 一起使用才是最强的，阿里巴巴的架构演进！ 技术没有高低之分，就看你如何去使用！（提升内功，思维的提高！）

### 阿里巴巴演进分析

思考问题：这么多东西难道都是在一个数据库中的吗?

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=M2IwYmFiOTI0NjE4YTAyYjcyYTk0MDU1MWQyZTkxMDFfcUtaQ2tQanY0bGtVSVA1TlN4cjU5dXVuRUQyNUZNeFVfVG9rZW46Ym94Y25lRTh2WjlZdGU1a1JmTUxzcU9hSkdoXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

技术急不得，越是慢慢学，才能越扎实！ 开源才是技术的王道！

任何一家互联网的公司，都不可能只是简简单单让用户能用就好了！

大量公司做的都是相同的业务；（竞品协议）

随着这样的竞争，业务是越来越完善，然后对于开发者的要求也是越来越高！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=YTIwOTE0YjlkZTIzODg5NjgwZDkxMmVjMzEwZTMwOTRfMDgyNWNES2pmakZKZjFDWXpCU2JxYlljRzZjV3ZQWUNfVG9rZW46Ym94Y25nbzVRcFNmMmlqVmR3cHM0RFRZUFdjXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

如果你未来相当一个架构师：   没有什么是加一层解决不了的！

要知道，一个简单地网页背后的技术一定不是大家所想的那么简单！ 大型互联网应用问题：

数据类型太多了！

数据源繁多，经常重构！ 数据要改造，大面积改造？

解决问题：

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=YTBiZGZjM2ZjYzQ0MjI3Yjg3ZDUyNTkyYjAwZTc5MWNfRFVubmRSeG9Qc0l1bm5tWTZPTlRBM2NLZnl6TjJPR0FfVG9rZW46Ym94Y252T0czMW5VZUZFZXk4Vlp5bGFmSklnXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=YjU3YjkwZDU0ZjczYzk0N2ZkOTVkYjllNDVhZWI4NGRfekdmWkRSeHBUdzVPQnpiQUc4YlRIZFJMcUs5UHpRcmFfVG9rZW46Ym94Y24zTXd0QXh1dGVXVGFvWERoSVhYaFNnXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

这里以上都是NoSQL入门概述，不仅能够提高大家的知识，还可以帮助大家了解大厂的工作内容！

### NoSQL的四大分类

##### KV键值对：

新浪：**Redis**

美团：Redis + Tair

阿里、百度：Redis + memecache

##### 文档型数据库（bson格式 和json一样）：

**MongoDB **（一般必须要掌握）

MongoDB 是一个基于分布式文件存储的数据库，C++ 编写，主要用来处理大量的文档！ MongoDB 是一个介于关系型数据库和非关系型数据中中间的产品！MongoDB 是非关系型数据库中功能最丰富，最像关系型数据库的！

ConthDB

##### 列存储数据库

**HBase**

分布式文件系统

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=NjJiMWZiMWM5MTYyNjA1ZDg5ZDAyYTAwYTQzZGJlMWFfNUtrRFJYMWg4TE4zeEEwYkQzUDZOV0x0NDVubWdoak1fVG9rZW46Ym94Y245NUZIYlFMeGdTYnF1bDZ3MWREZ0NiXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

图关系数据库

他不是存图形，放的是关系，比如：朋友圈社交网络，广告推荐！

**Neo4j**，InfoGrid；

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=OWY5ODcwMDcyMDRjN2QzZWNkNjdiOWM0NjBkYzljOTdfTUdrbGRNRnVPS3ZSaU1BSEdPazBpTHJ1dW5MWTV2MXZfVG9rZW46Ym94Y25qVE83STdwemQ5dnE5eWI1S211c1dlXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

敬畏之心可以使人进步！宇宙！科幻！

活着的意义？    追求幸福（帮助他人，感恩之心），探索未知（努力的学习，不要这个社会抛弃）

## Redis入门

### 概述

Redis（Remote Dictionary Server )，即远程字典服务 !

是一个开源的使用ANSI [C](https://baike.baidu.com/item/C语言)[语言](https://baike.baidu.com/item/C语言)编写、支持网络、可基于内存亦可持久化的日志型、Key-Value[数据库](https://baike.baidu.com/item/数据库/103728)， 并提供多种语言的API。

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=MDI2MmU2MTY5ZGU5YmI3NDgwMTY5Y2MwMjk4YTI2ZGNfZ0QxblZiRkpHaHZKYXNaRkowRzlxczg4TUNVamtQdFJfVG9rZW46Ym94Y25lZ1haYzlQbHFRN0NYazhwZExtQmViXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

redis会周期性的把更新的数据写入磁盘或者把修改操作写入追加的记录文件，并且在此基础上实现了master-slave(主从)同步。

免费和开源！是当下最热门的 NoSQL 技术之一！也被人们称之为结构化数据库！

1、内存存储、持久化，内存中是断电即失、所以说持久化很重要（rdb、aof）

2、效率高，可以用于高速缓存

3、发布订阅系统

4、地图信息分析

5、计时器、计数器（浏览量！）

6、........

1、多样的数据类型

2、持久化

3、集群

4、事务

......

1、狂神的公众号：狂神说2、官网：[https://redis.io/](https://redis.io/)

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=Y2YxYTA0ODc1NGU4YjE1NDVkOGVjYTkzODEwMDJhMzhfYnVnZUNOMW1pM2hRVnN5cE5DaEhrOUNpdDdJaTZPbFpfVG9rZW46Ym94Y25OekYyUk1MeWY2OWdDcEY1aXBqZXlmXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

3、中文网：[http://www.redis.cn/](http://www.redis.cn/)4、下载地址：通过官网下载即可！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=OGEzMmE3MzEyMmY0MmE0MzY3ZDRmZDI5NDFhYzJmYzNfemxpdXpDYldYMFVGZTRQd3c3dldBQTFOU045Nm1Fc3RfVG9rZW46Ym94Y255V1BpSW9zV0Vkckp4TjBaSXBKQ25nXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

注意：Wdinow在 Github上下载（停更很久了！）

Redis推荐都是在Linux服务器上搭建的，我们是基于Linux学习！

### Windows安装

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=ZjE3MzVlNzM1YmFlNWVhNjQwOTQwYjYxYTQzZjA3YTBfemRyVk40OUdJMFdNb1U0Vnp1OWVNMDBEOVQ1bWptcHBfVG9rZW46Ym94Y250d2hKbFpOQXJ2bmhsc2NiOWR5YW1iXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

1、下载安装包：[https://github.com/dmajkic/redis/releases](https://github.com/dmajkic/redis/releases)2、下载完毕得到压缩包：

3、解压到自己电脑上的环境目录下的就可以的！Redis   十分的小，只有5M

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=NjczMWQzYTdiZDNiZmY1YWRiYWYzY2JlMmRjZGE3ZDhfOFp3TFJjSXlwOUZkcXF6cWV0NW1BM1k2UVBTdTFmSFhfVG9rZW46Ym94Y25BazNSYnhTZVR4Tlo4WkRtdTNYV2ZnXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

4、开启Redis，双击运行服务即可！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=ZGQ3NjkyMTY3OTQwOTlkNmZkYmFmYzk3ZWRiZTQ0YjJfQVdPakEwVHUyeXBEQ2ptMFNqZzFwN2g2bmZMRE1wcGdfVG9rZW46Ym94Y25vTDh4dzBSZm9zZ2IzOXJQb0J1MXplXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

5、使用redis客户单来来连接redis

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=NjNhYWFmZmU3Mjg3ODlhODcwYTA5OTM1ZWE1NWMzNzRfeTN1ZWJSdjhvVXM0c3lkVmhLOE9WemVHelBETjVHRTNfVG9rZW46Ym94Y25zUDM1Z2liUjhPeUFvYUVSbTBVSURnXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

记住一句话，Window下使用确实简单，但是Redis    推荐我们使用Linux去开发使用！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=NDNiZDA0OGJhZmQ5NGRmYmYxNTQ1YmVlMjk1ZGY0MmJfdTBITGhydWxNaG1FRFRXZFU0c0g4SHIxa1FNMmtDa2FfVG9rZW46Ym94Y25mUFNTSUpaRE53MTI1dTFBSmFxa1FjXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

### Linux安装

1、下载安装包！

2、解压Redis的安装包！ 程序/opt

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=OGU1ZjcwMDBmMjk2N2YxMzQxZTZmNWIzMDBlNjEwMmRfWG5tc1RTVzlnejlOTVNxSFgyWEZZenVPd1JIZUp5OTFfVG9rZW46Ym94Y255ZFBkODJibzgwVUpPV1gzTDVSQ29jXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

3、进入解压后的文件，可以看到我们redis的配置文件

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=YjYwNDA4NWNjYzA5OTZhNTFhYTk5OWE1MTE3Nzc5NThfT3dnc1hqNmxWbjkzb3FLcjEyamVjU0pqN29DYUZlbG1fVG9rZW46Ym94Y244QUsxZjAwRUlPdFBQMFBMdXNIRkRnXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

4、基本的环境安装

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=YTRkZTQwYTIwMTU1ZGY3ODNkYWUwZjUyOGI0YjI0YzlfOXV6R2NuNjhCbnRidGRuWk1VTHhvZmYxcVBHT2d0YUZfVG9rZW46Ym94Y25oaDYxQzd1WmQ1QlB0NkR1SU41dlhGXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=YThmNTU1MzBhMGY3ZmJhZmE4YTk5ZmU0YWY2NzE2MDdfaU9aNVVGQ2plNmRNTVlqczIwTzZaRHIwWU10NktlVHVfVG9rZW46Ym94Y25IQUhLOHZKNFBxZHhsa0lHNkF4NVBlXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

5、redis的默认安装路径

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=Y2YzMDQwYTdjNzhiYzk3Yjg1ZTFjNGI5YTliMzYxNTFfMmdZRkhkNFJFQThGZlJtVHNjUXF5VVJsN1NLWG9adHZfVG9rZW46Ym94Y25xdEdzS0JWcUxuVzIxMUlCdkhoQ2dnXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

6、将redis配置文件。复制到我们当前目录下

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=OTY4ZDIwMzUxMjNkYjkwZmZjYTExNGEyOGY1ZTA3MTVfSnFEWWpMTUtqVDFQUHM3dGVKWmJ2TlFQSVdqREJ5VjNfVG9rZW46Ym94Y25KMmozbEpNVFhxMm14bThQdmIya01kXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

7、redis默认不是后台启动的，修改配置文件！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=ZjI0ZDc0Mzg0NTUyZDgzMTI3NjZhYTVmMWQ4NzBiYjdfUVhTWGdQMGtYUVdkdWo3NEdtSnNwNE5NbDlKa29hSXdfVG9rZW46Ym94Y25jN1hVUzNmZDVuZDA1RHpzY2p1UnBjXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

8、启动Redis服务！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=MTU3ZDg3YjY1ZmRjNDNmNmY2NjhmZjRkZjk0MTI4MjJfSDZ3VG9CTk5WYXMwSUJKTzVzNktsNzFuVXhpbVoybmtfVG9rZW46Ym94Y25nSE5FazhEWjZMRDJKZlh4NnpTcTFiXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

9、使用redis-cli 进行连接测试！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=MTQ1ZTA3MTFmODFiMjc1M2UxOWUxYjZmMDdhOTdhNjBfSTJHT0U1R29vT1g2QUJybVhveXBLR09tSFdpcHgwcmxfVG9rZW46Ym94Y243Mk90elhoOUdWcnRqSFRDdXVGWXY2XzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

10、查看redis的进程是否开启！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=MTIxYmM1YzNiN2ExYTQzYmU2ZWNkZjJlYzhjNjAxYjlfS09LVFVOWVJuTjJ0RjFSc3RabnFoQ0QxVmpnWlBFMTZfVG9rZW46Ym94Y25CYkZzRFphRzhPWDlibWZOc0UxQW1lXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

11、如何关闭Redis服务呢？

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=NmJiZjI5OTJkYWJhYzNkMGFmNTYzMGU0ZWU2OGJhMmZfUEgzczlmbmFLOFhSUW5WUGF5bFVGN1FYOFp1VVQyUkNfVG9rZW46Ym94Y25BQ0l6emFsM2g4NUZPSHU3V0w3blRNXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

12、再次查看进程是否存在

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=ZDkwNDY0NDU1MjZkMWEyOGNjYTg2MTJkODA2OTNkM2FfUWJBYkp3TG1JOFVvZU9obmpra2FITmtSU2ZrenBDVkpfVG9rZW46Ym94Y25SdzJOY2txUmN1ektRbUxzTWJmZWJiXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

13、后面我们会使用单机多Redis启动集群测试！

### 测试性能

**redis-benchmark **是一个压力测试工具！ 官方自带的性能测试工具！

redis-benchmark 命令参数！

图片来自菜鸟教程：

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=OTllODBiZWI3MGRkZDYxNGFjMjFhNWE2MTcxMWMwZWVfVFBseGFqOTRxSXRYUGp0WjZtakdMNkN4RW53aDdjdDRfVG9rZW46Ym94Y25Nck5tNGIxZ29FQlpzWUZ3ajFxRnM5XzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

我们来简单测试下：

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=NzgwOTUxZjEzZmM2ZGYzOTc4ZTBjMGFlMzhhNWVkNjBfMEM2emNVMGQ1RU14a2RIRzQwbTE0ODNObERRMzlJanlfVG9rZW46Ym94Y25ubzlHblJEbGhnMTl2eHY0ZnFTd2NoXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

如何查看这些分析呢？

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=MWViNTVjNzcyYzk5M2E1OWI3ODg0MGIwZGQ0ZTIwZDRfc0ZLczFPR0piaExKRHNIZ25OZndSc2RFV0NlV1QzSjZfVG9rZW46Ym94Y25ybEoxMDc4eWE4b0x3RlFmRWtCREpoXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

### 基础的知识

redis默认有16个数据库

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=ODc3MDlkNDE3MjQ1YWJiYmU4ZDQ4OTY4NmRlMzhmMjNfenk4UmoyWG9XZWw0N0FMeTB5ckxVUVhXNU9GZDdaRVlfVG9rZW46Ym94Y244ZUpxVFpzMXh3YVp1eldtV3l0TWxiXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

默认使用的是第0个

可以使用 select 进行切换数据库！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=OWJkZGFjZTg1Y2Y4ZGY4NDM1MGU2NjUwODUwOTBmMTZfTzkzRWp0M1BBQkx2Y1M0MjA4dE1LZ2JRNWc4SWdNZ2VfVG9rZW46Ym94Y25RRkF1SUp4RUdNU3RzSmFnc1ZTOVdnXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

清除当前数据库

清除全部数据库的内容

思考：为什么redis是   6379！粉丝效应！（了解一下即可！）

明白Redis是很快的，官方表示，Redis是基于内存操作，CPU不是Redis性能瓶颈，Redis的瓶颈是根据 机器的内存和网络带宽，既然可以使用单线程来实现，就使用单线程了！所有就使用了单线程了！

Redis 是C 语言写的，官方提供的数据为 100000+ 的QPS，完全不比同样是使用 key-vale的Memecache差！

##### Redis 为什么单线程还这么快？

1、误区1：高性能的服务器一定是多线程的？

2、误区2：多线程（CPU上下文会切换！）一定比单线程效率高！ 先去CPU>内存>硬盘的速度要有所了解！

核心：redis     是将所有的数据全部放在内存中的，所以说使用单线程去操作效率就是最高的，多线程

（CPU上下文会切换：耗时的操作！！！），对于内存系统来说，如果没有上下文切换效率就是最高 的！多次读写都是在一个CPU上的，在内存情况下，这个就是最佳的方案！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=NmZlMGYwZWNiNGRkZjdlNmEzYmEyYjdhY2JiMzQ2ZGFfaFhEN1BMU0szdkZQdlNSZ1BFZ0s1dGhLSWNHa2ZuNUFfVG9rZW46Ym94Y25LQjd1MkVidWtsRUp6amxNTlNJM2xiXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

五大数据类型

全段翻译：

Redis   是一个开源（BSD许可）的，内存中的数据结构存储系统，它可以用作数据库、缓存和消息中间件MQ。 它支持多种类型的数据结构，如 字符串（strings）， 散列（hashes）， 列表（lists）， 集合

（sets）， 有序集合（sorted sets） 与范围查询， bitmaps， hyperloglogs 和 地理空间

（geospatial） 索引半径查询。 Redis 内置了 复制（replication），LUA脚本（Lua scripting）， LRU 驱动事件（LRU eviction），事务（transactions） 和不同级别的 磁盘持久化（persistence）， 并通过Redis哨兵（Sentinel）和自动 分区（Cluster）提供高可用性（high availability）。

### Redis-Key

127.0.0.1:6379> keys *# 查看所有的key (empty list or set)

127.0.0.1:6379> set name kuangshen # set key OK

127.0.0.1:6379> keys *

1) "name" 127.0.0.1:6379> set age 1 OK

127.0.0.1:6379> keys *

1. "age"
2. "name"

127.0.0.1:6379> EXISTS name # 判断当前的key是否存在

(integer) 1

127.0.0.1:6379> EXISTS name1

(integer) 0

127.0.0.1:6379> move name 1 # 移除当前的key

(integer) 1

127.0.0.1:6379> keys *

1) "age"

127.0.0.1:6379> set name qinjiang OK

127.0.0.1:6379> keys *

1. "age"
2. "name" 127.0.0.1:6379> clear 127.0.0.1:6379> keys *
3. "age"
4. "name" 127.0.0.1:6379> get name "qinjiang"

127.0.0.1:6379> EXPIRE name 10 # 设置key的过期时间，单位是秒

(integer) 1

127.0.0.1:6379> ttl name # 查看当前key的剩余时间

(integer) 4

127.0.0.1:6379> ttl name

(integer) 3

127.0.0.1:6379> ttl name

(integer) 2

127.0.0.1:6379> ttl name

(integer) 1

127.0.0.1:6379> ttl name

(integer) -2

127.0.0.1:6379> get name (nil)

127.0.0.1:6379> type name # 查看当前key的一个类型！

string

127.0.0.1:6379> type age string

后面如果遇到不会的命令，可以在官网查看帮助文档！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=MDI4Y2Y4ZjE5NWI5NjQyN2QwMjJmN2E4MDkzY2Q3YWVfYWdyWXBUQ0Z5UlhRNXhmMFp5NnBhejhuSkxyZXJSaE9fVG9rZW46Ym94Y255MGwyUVFqWlpJZnZSeFNOQjdUOGxnXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

### String（字符串）

90% 的 java程序员使用 redis 只会使用一个String类型！

##########################################################################

127.0.0.1:6379> set key1 v1# 设置值

OK

127.0.0.1:6379> get key1# 获得值

"v1"

127.0.0.1:6379> keys *# 获得所有的key

1) "key1"

127.0.0.1:6379> EXISTS key1# 判断某一个key是否存在

(integer) 1

127.0.0.1:6379> APPEND key1 "hello" # 追加字符串，如果当前key不存在，就相当于setkey (integer) 7

127.0.0.1:6379> get key1

"v1hello"

127.0.0.1:6379> STRLEN key1 # 获取字符串的长度！

(integer) 7

127.0.0.1:6379> APPEND key1 ",kaungshen" (integer) 17

127.0.0.1:6379> STRLEN key1

(integer) 17

127.0.0.1:6379> get key1

"v1hello,kaungshen" ########################################################################## # i++

# 步长 i+=

127.0.0.1:6379> set views 0 # 初始浏览量为0 OK

127.0.0.1:6379> get views

"0"

127.0.0.1:6379> incr views # 自增1 浏览量变为1 (integer) 1

127.0.0.1:6379> incr views

(integer) 2

127.0.0.1:6379> get views

"2"

127.0.0.1:6379> decr views # 自减1浏览量-1 (integer) 1

127.0.0.1:6379> decr views

(integer) 0

127.0.0.1:6379> decr views

(integer) -1

127.0.0.1:6379> get views

"-1"

127.0.0.1:6379> INCRBY views 10 # 可以设置步长，指定增量！

(integer) 9

127.0.0.1:6379> INCRBY views 10

(integer) 19

127.0.0.1:6379> DECRBY views 5

(integer) 14

##########################################################################

# 字符串范围 range

127.0.0.1:6379> set key1 "hello,kuangshen" # 设置 key1 的值

OK

127.0.0.1:6379> get key1

"hello,kuangshen"

127.0.0.1:6379> GETRANGE key1 0 3# 截取字符串 [0,3]

"hell"

127.0.0.1:6379> GETRANGE key1 0 -1# 获取全部的字符串 和 get key是一样的"hello,kuangshen"

# 替换！

127.0.0.1:6379> set key2 abcdefg OK

127.0.0.1:6379> get key2

"abcdefg"

127.0.0.1:6379> SETRANGE key2 1 xx # 替换指定位置开始的字符串！

(integer) 7

127.0.0.1:6379> get key2

"axxdefg" ##########################################################################

# setex (set with expire)# 设置过期时间

# setnx (set if not exist)# 不存在在设置 （在分布式锁中会常常使用！） 127.0.0.1:6379> setex key3 30 "hello" # 设置key3 的值为 hello,30秒后过期OK

127.0.0.1:6379> ttl key3

(integer) 26

127.0.0.1:6379> get key3

"hello"

127.0.0.1:6379> setnx mykey "redis"# 如果mykey 不存在，创建mykey (integer) 1

127.0.0.1:6379> keys *

1. "key2"
2. "mykey"
3. "key1" 127.0.0.1:6379> ttl key3 (integer) -2

127.0.0.1:6379> setnx mykey "MongoDB"# 如果mykey存在，创建失败！

(integer) 0

127.0.0.1:6379> get mykey "redis"

##########################################################################

mset mget

127.0.0.1:6379> mset k1 v1 k2 v2 k3 v3 # 同时设置多个值

OK

127.0.0.1:6379> keys *

1) "k1"
2) "k2"
3) "k3"

127.0.0.1:6379> mget k1 k2 k3# 同时获取多个值

1) "v1"
2) "v2"
3) "v3"

数据结构是相同的！

String类似的使用场景：value除了是我们的字符串还可以是我们的数字！

计数器

统计多单位的数量粉丝数

对象缓存存储！

### List（列表）

基本的数据类型，列表

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=MDM4N2YxOTFlZjJjMTU4MDJiMTU3OWFjZTA4ZDMxODhfeG1Fdk1YcUF4ZUtXM1h0WGFZQnNRb3lIZUFqcHFHZVFfVG9rZW46Ym94Y24zTWpzeWE2RTV0ZjBTbFlZTW96SFFjXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

在redis里面，我们可以把list玩成 ，栈、队列、阻塞队列！ 所有的list命令都是用l开头的，Redis不区分大小命令

##########################################################################

127.0.0.1:6379> LPUSH list one # 将一个值或者多个值，插入到列表头部 （左）

(integer) 1

127.0.0.1:6379> LPUSH list two

(integer) 2

127.0.0.1:6379> LPUSH list three (integer) 3

127.0.0.1:6379> LRANGE list 0 -1# 获取list中值！

1. "three"
2. "two"
3. "one"

127.0.0.1:6379> LRANGE list 0 1# 通过区间获取具体的值！

1. "three"
2. "two"

127.0.0.1:6379> Rpush list righr # 将一个值或者多个值，插入到列表位部 （右）

(integer) 4

127.0.0.1:6379> LRANGE list 0 -1

1. "three"
2. "two"
3. "one"
4. "righr"

########################################################################## LPOP

RPOP

127.0.0.1:6379> LRANGE list 0 -1

1. "three"
2. "two"
3. "one"
4. "righr"

127.0.0.1:6379> Lpop list # 移除list的第一个元素

"three"

127.0.0.1:6379> Rpop list # 移除list的最后一个元素

"righr"

127.0.0.1:6379> LRANGE list 0 -1

1. "two"
2. "one" ########################################################################## Lindex

127.0.0.1:6379> LRANGE list 0 -1

1. "two"
2. "one"

127.0.0.1:6379> lindex list 1 # 通过下标获得 list 中的某一个值！

"one"

127.0.0.1:6379> lindex list 0 "two"

##########################################################################

Llen

127.0.0.1:6379> Lpush list one (integer) 1

127.0.0.1:6379> Lpush list two

(integer) 2

127.0.0.1:6379> Lpush list three (integer) 3

127.0.0.1:6379> Llen list # 返回列表的长度

(integer) 3

##########################################################################

移除指定的值！ 取关 uid

Lrem

127.0.0.1:6379> LRANGE list 0 -1

1. "three"
2. "three"
3. "two"
4. "one"

127.0.0.1:6379> lrem list 1 one # 移除list集合中指定个数的value，精确匹配

(integer) 1

127.0.0.1:6379> LRANGE list 0 -1

1. "three"
2. "three"
3. "two"

127.0.0.1:6379> lrem list 1 three

(integer) 1

127.0.0.1:6379> LRANGE list 0 -1

1. "three"
2. "two"

127.0.0.1:6379> Lpush list three (integer) 3

127.0.0.1:6379> lrem list 2 three

(integer) 2

127.0.0.1:6379> LRANGE list 0 -1

1) "two"

##########################################################################

trim 修剪。； list 截断!

127.0.0.1:6379> keys *

(empty list or set)

127.0.0.1:6379> Rpush mylist "hello" (integer) 1

127.0.0.1:6379> Rpush mylist "hello1" (integer) 2

127.0.0.1:6379> Rpush mylist "hello2" (integer) 3

127.0.0.1:6379> Rpush mylist "hello3" (integer) 4

127.0.0.1:6379> ltrim mylist 1 2# 通过下标截取指定的长度，这个list已经被改变了，截断了

只剩下截取的元素！

OK

127.0.0.1:6379> LRANGE mylist 0 -1

1. "hello1"
2. "hello2"

##########################################################################

rpoplpush # 移除列表的最后一个元素，将他移动到新的列表中！

127.0.0.1:6379> rpush mylist "hello"

(integer) 1

127.0.0.1:6379> rpush mylist "hello1" (integer) 2

127.0.0.1:6379> rpush mylist "hello2" (integer) 3

127.0.0.1:6379> rpoplpush mylist myotherlist # 移除列表的最后一个元素，将他移动到新的

列表中！

"hello2"

127.0.0.1:6379> lrange mylist 0 -1 # 查看原来的列表

1. "hello"
2. "hello1"

127.0.0.1:6379> lrange myotherlist 0 -1 # 查看目标列表中，确实存在改值！

1) "hello2"

##########################################################################

lset 将列表中指定下标的值替换为另外一个值，更新操作127.0.0.1:6379> EXISTS list # 判断这个列表是否存在(integer) 0

127.0.0.1:6379> lset list 0 item# 如果不存在列表我们去更新就会报错

(error) ERR no such key 127.0.0.1:6379> lpush list value1 (integer) 1

127.0.0.1:6379> LRANGE list 0 0

1) "value1"

127.0.0.1:6379> lset list 0 item# 如果存在，更新当前下标的值

OK

127.0.0.1:6379> LRANGE list 0 0

1) "item"

127.0.0.1:6379> lset list 1 other# 如果不存在，则会报错！

(error) ERR index out of range ##########################################################################

linsert # 将某个具体的value插入到列把你中某个元素的前面或者后面！

127.0.0.1:6379> Rpush mylist "hello" (integer) 1

127.0.0.1:6379> Rpush mylist "world" (integer) 2

127.0.0.1:6379> LINSERT mylist before "world" "other" (integer) 3

127.0.0.1:6379> LRANGE mylist 0 -1

1. "hello"
2. "other"
3. "world"

127.0.0.1:6379> LINSERT mylist after world new (integer) 4

127.0.0.1:6379> LRANGE mylist 0 -1

1. "hello"
2. "other"
3. "world"
4. "new"

他实际上是一个链表，before Node after ， left，right 都可以插入值如果key 不存在，创建新的链表

如果key存在，新增内容

如果移除了所有值，空链表，也代表不存在！

在两边插入或者改动值，效率最高！   中间元素，相对来说效率会低一点~

消息排队！消息队列 （Lpush Rpop），  栈（ Lpush Lpop）！

### Set（集合）

set中的值是不能重读的！

##########################################################################

127.0.0.1:6379> sadd myset "hello"# set集合中添加匀速(integer) 1

127.0.0.1:6379> sadd myset "kuangshen" (integer) 1

127.0.0.1:6379> sadd myset "lovekuangshen" (integer) 1

127.0.0.1:6379> SMEMBERS myset# 查看指定set的所有值

1. "hello"
2. "lovekuangshen"
3. "kuangshen"

127.0.0.1:6379> SISMEMBER myset hello# 判断某一个值是不是在set集合中！

(integer) 1

127.0.0.1:6379> SISMEMBER myset world

(integer) 0

##########################################################################

127.0.0.1:6379> scard myset # 获取set集合中的内容元素个数！

(integer) 4

##########################################################################

rem

127.0.0.1:6379> srem myset hello # 移除set集合中的指定元素

(integer) 1

127.0.0.1:6379> scard myset

(integer) 3

127.0.0.1:6379> SMEMBERS myset

1. "lovekuangshen2"
2. "lovekuangshen"
3. "kuangshen"

##########################################################################

set 无序不重复集合。抽随机！

127.0.0.1:6379> SMEMBERS myset

1. "lovekuangshen2"
2. "lovekuangshen"
3. "kuangshen"

127.0.0.1:6379> SRANDMEMBER myset # 随机抽选出一个元素

"kuangshen"

127.0.0.1:6379> SRANDMEMBER myset

"kuangshen"

127.0.0.1:6379> SRANDMEMBER myset

"kuangshen"

127.0.0.1:6379> SRANDMEMBER myset

"kuangshen"

127.0.0.1:6379> SRANDMEMBER myset 2 # 随机抽选出指定个数的元素

1. "lovekuangshen"
2. "lovekuangshen2"

127.0.0.1:6379> SRANDMEMBER myset 2

1. "lovekuangshen"
2. "lovekuangshen2"

127.0.0.1:6379> SRANDMEMBER myset# 随机抽选出一个元素

"lovekuangshen2"

##########################################################################

删除定的key，随机删除key！

127.0.0.1:6379> SMEMBERS myset

1. "lovekuangshen2"
2. "lovekuangshen"
3. "kuangshen"

127.0.0.1:6379> spop myset # 随机删除一些set集合中的元素！

"lovekuangshen2" 127.0.0.1:6379> spop myset "lovekuangshen" 127.0.0.1:6379> SMEMBERS myset

1) "kuangshen"

##########################################################################

将一个指定的值，移动到另外一个set集合！ 127.0.0.1:6379> sadd myset "hello" (integer) 1

127.0.0.1:6379> sadd myset "world" (integer) 1

127.0.0.1:6379> sadd myset "kuangshen" (integer) 1

127.0.0.1:6379> sadd myset2 "set2" (integer) 1

127.0.0.1:6379> smove myset myset2 "kuangshen" # 将一个指定的值，移动到另外一个set集

合！

(integer) 1

127.0.0.1:6379> SMEMBERS myset

1. "world"
2. "hello"

127.0.0.1:6379> SMEMBERS myset2

1. "kuangshen"
2. "set2"

##########################################################################

微博，B站，共同关注！(并集) 数字集合类：

* 差集 SDIFF
* 交集
* 并集

127.0.0.1:6379> SDIFF key1 key2# 差集

1) "b"
2) "a"

127.0.0.1:6379> SINTER key1 key2# 交集共同好友就可以这样实现

1) "c"

127.0.0.1:6379> SUNION key1 key2# 并集

1) "b"
2) "c"
3) "e"
4) "a"

微博，A用户将所有关注的人放在一个set集合中！将它的粉丝也放在一个集合中！ 共同关注，共同爱好，二度好友，推荐好友！（六度分割理论）

### Hash（哈希）

Map集合，key-map!    时候这个值是一个map集合！ 本质和String类型没有太大区别，还是一个简单的

key-vlaue！

set myhash ﬁeld kuangshen

hash变更的数据 user name age,尤其是是用户信息之类的，经常变动的信息！ hash 更适合于对象的存储，String更加适合字符串存储！

### Zset（有序集合）

在set的基础上，增加了一个值，set k1 v1   zset k1 score1 v1

其与的一些API，通过我们的学习吗，你们剩下的如果工作中有需要，这个时候你可以去查查看官方文 档！

案例思路：set 排序 存储班级成绩表，工资表排序！ 普通消息，1， 重要消息 2，带权重进行判断！

排行榜应用实现，取Top N 测试！

## 三种特殊数据类型

### Geospatial 地理位置

朋友的定位，附近的人，打车距离计算？

Redis 的 Geo 在Redis3.2 版本就推出了！ 这个功能可以推算地理位置的信息，两地之间的距离，方圆几里的人！

可以查询一些测试数据：[http://www.jsons.cn/lngcodeinfo/0706D99C19A781A3/](http://www.jsons.cn/lngcodeinfo/0706D99C19A781A3/)[](http://www.jsons.cn/lngcodeinfo/0706D99C19A781A3/)只有 六个命令：

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=MWQzODcxNzBiZjBhMTIyMDdmY2NiZjZiM2UyMWRhMTZfenhxRHVxTmkwVkh1SGxaenNWR09Sb3dFZXZDQkF1NFFfVG9rZW46Ym94Y25nYmMxaWR6ZDk5Z3l0QmQxNWdlMlFnXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

、

官方文档：[https://www.redis.net.cn/order/3685.html](https://www.redis.net.cn/order/3685.html)

| 127.0.0.1:6379> | geoadd | china:city | 116.40 | 39.90 | beijing            |           |
| --------------- | ------ | ---------- | ------ | ----- | ------------------ | --------- |
| (integer)1      |        |            |        |       |                    |           |
| 127.0.0.1:6379> | geoadd | china:city | 121.47 | 31.23 | shanghai           |           |
| (integer)1      |        |            |        |       |                    |           |
| 127.0.0.1:6379> | geoadd | china:city | 106.50 | 29.53 | chongqi114.0522.52 | shengzhen |
| (integer)2      |        |            |        |       |                    |           |

获得当前定位：一定是一个坐标值！

两人之间的距离！ 单位：

**m **表示单位为米。**km **表示单位为千米。**mi **表示单位为英里。**ft **表示单位为英尺。

我附近的人？ （获得所有附近的人的地址，定位！）通过半径来查询！ 获得指定数量的人，200

所有数据应该都录入：china:city   ，才会让结果更加请求！

| 127.0.0.1:6379> GEORADIUS          | china:city | 110 | 30 | 500 | km |                                |
| ---------------------------------- | ---------- | --- | -- | --- | -- | ------------------------------ |
| 1)"chongqi"                        |            |     |    |     |    |                                |
| 2)"xian" 127.0.0.1:6379> GEORADIUS | china:city | 110 | 30 | 500 | km | withdist# 显示到中间距离的位置 |

该命令将返回11个字符的Geohash字符串!

### Hyperloglog

A {1,3,5,7,8,7}

B{1，3,5,7,8}

基数（不重复的元素） = 5，可以接受误差！

Redis 2.8.9 版本就更新了 Hyperloglog 数据结构！

Redis Hyperloglog 基数统计的算法！

优点：占用的内存是固定，2^64 不同的元素的技术，只需要废 12KB内存！如果要从内存角度来比较的话 Hyperloglog 首选！

##### 网页的 UV （一个人访问一个网站多次，但是还是算作一个人！）

传统的方式， set 保存用户的id，然后就可以统计 set 中的元素数量作为标准判断 !

这个方式如果保存大量的用户id，就会比较麻烦！我们的目的是为了计数，而不是保存用户id；

0.81% 错误率！ 统计UV任务，可以忽略不计的！

如果允许容错，那么一定可以使用 Hyperloglog   ！

如果不允许容错，就使用 set 或者自己的数据类型即可！

### Bitmap

为什么其他教程都不喜欢讲这些？这些在生活中或者开发中，都有十分多的应用场景，学习了，就是就 是多一个思路！

技多不压身！

统计用户信息，活跃，不活跃！ 登录 、 未登录！ 打卡，365打卡！ 两个状态的，都可以使用

Bitmaps！

Bitmap 位图，数据结构！ 都是操作二进制位来进行记录，就只有0 和 1 两个状态！

365 天 = 365 bit  1字节 = 8bit46 个字节左右！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=YjlhNzEzZTcwMDkyMTE2MDkwODllZWY0ODZkOWZjOTVfSWVJOW5KV2J1ZWpobUZVSk8zY0hOV0lOclkxN2lteEdfVG9rZW46Ym94Y25iWWhmMzlva2hIUFpVTTdPcHViWXlnXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=YTEzMDA0MWQ5NGNlNzRmMTk5ODk3ZTdlZTBhZmI4NjNfYWJwcm5iQ1dmQmpVaHZ5NWVXQW9vcEplZE1WdkZWS2FfVG9rZW46Ym94Y25yNTRUeFFzeThHU3RFTThtT0tSdmVjXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

使用bitmap 来记录 周一到周日的打卡！ 周一：1 周二：0 周三：0 周四：1 ......

查看某一天是否有打卡！

统计操作，统计 打卡的天数！

## 事务

Redis 事务本质：一组命令的集合！ 一个事务中的所有命令都会被序列化，在事务执行过程的中，会按照顺序执行！

一次性、顺序性、排他性！执行一些列的命令！

Redis事务没有没有隔离级别的概念！

所有的命令在事务中，并没有直接被执行！只有发起执行命令的时候才会执行！Exec Redis单条命令式保存原子性的，但是事务不保证原子性！

redis的事务：

开启事务（multi） 命令入队（）

执行事务（exec）

##### 悲观锁：

很悲观，认为什么时候都会出问题，无论做什么都会加锁！

##### 乐观锁：

很乐观，认为什么时候都不会出问题，所以不会上锁！ 更新数据的时候去判断一下，在此期间是否有人修改过这个数据，

获取version

更新的时候比较 version

正常执行成功！

测试多线程修改值 , 使用watch 可以当做redis的乐观锁操作！

如果修改失败，获取最新的值就好

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=MWRmZGQxYjNhMzc5NjEzODc0MDgzMWZiYWI3NmM4MGZfNHRSMjRvVEVqaTl0MG03M1Y1azZLN1RQeUxzWDFoV0dfVG9rZW46Ym94Y25pb3MyQ251QnFWTWd2YnF4bERjNU1kXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

## Jedis

我们要使用 Java 来操作 Redis，知其然并知其所以然，授人以渔！ 学习不能急躁，慢慢来会很快！

1、导入对应的依赖

2、编码测试：

连接数据库操作命令断开连接！

输出：

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=YjllOTdjN2U4YmEyMzAyY2U0ZWVhMzM1N2VjZTIyNjBfc0t1aFI3MVZ1cXdacHJZcjFWT3BLMkUwRDhnaFduT1NfVG9rZW46Ym94Y25ua0VqNUZBUVZ3ZzZ0ZHJFT0xpN29nXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

### 常用的API

String List Set Hash Zset

## SpringBoot整合

SpringBoot 操作数据：spring-data jpa jdbc mongodb redis！ SpringData 也是和 SpringBoot 齐名的项目！

说明： 在 SpringBoot2.x 之后，原来使用的jedis 被替换为了 lettuce?

jedis : 采用的直连，多个线程操作的话，是不安全的，如果想要避免不安全的，使用 jedis pool 连接池！ 更像 BIO 模式

lettuce : 采用netty，实例可以再多个线程中进行共享，不存在线程不安全的情况！可以减少线程数据了，更像 NIO 模式

源码分析：

1、导入依赖

2、配置连接

3、测试！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=ODQ5NWIwMjBlZjI5MWY2Nzg4NDJiNTZlNGE3ZjljYzdfWjBZNlVob3ZFN2pLWUY0RTdEY1pzeTFXRjhYTmdCNVhfVG9rZW46Ym94Y25YUUpiMVFqM0RldFhaV0F1SUJRWjZiXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=NThlN2ViYzYzNTliOTBmMWQ2Y2RkZmQxNWU0NzhjNjVfM3U0QkltOWE1UGhEaDIybjhmazdUdVY3YXhQN296SjBfVG9rZW46Ym94Y25LZFoxVk1NZWRNbmFFdXVtQ0ZrZm1lXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

关于对象的保存：

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=ZDk5MTVhZWU4NDJjNjc3YjQ1N2I4N2VlZjJlMjlmODVfbHhmN0dsMXJuZFBDTFY2MEpHWFlBdjM4cnpWdXppSTVfVG9rZW46Ym94Y25NUmdxdWpPbDducTdzc2RuSGtuekVoXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

我们来编写一个自己的 RedisTemplete

所有的redis操作，其实对于java开发人员来说，十分的简单，更重要是要去理解redis的思想和每一种数 据结构的用处和作用场景！

## Redis.conf详解

启动的时候，就通过配置文件来启动！

工作中，一些小小的配置，可以让你脱颖而出！ 行家有没有，出手就知道

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=NDc1YTMwMmIyMWQyYTY4OTU5ZDIxOGY5NmY0ZjA0ZjhfRHVKS3JIbDFiTGc3UFMzVkdVaDBiSHh3R2R2R3NoOHhfVG9rZW46Ym94Y25GYklvalJCOThWN25pQUtnZ2ZNU2dlXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

1、配置文件 unit单位 对大小写不敏感！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=ODNjOGM3OWFkNjA5NjI0YmNiY2ZhMWU5MzIwOGM1OWNfWHNTQ1I3cllOVENiMUNGUk05QnR5b3Z6TjNCNWxESHhfVG9rZW46Ym94Y250YWNlUE4xc2ZIMVB5SHg2RlhScGpHXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

就是好比我们学习Spring、Improt， include

持久化， 在规定的时间内，执行了多少次操作，则会持久化到文件 .rdb. aof redis 是内存数据库，如果没有持久化，那么数据断电及失！

可以在这里设置redis的密码，默认是没有密码！

具体的配置，我们在 Redis持久化 中去给大家详细详解！

## Redis持久化

面试和工作，持久化都是重点！

Redis 是内存数据库，如果不将内存中的数据库状态保存到磁盘，那么一旦服务器进程退出，服务器中的数据库状态也会消失。所以 Redis 提供了持久化功能！

#### RDB（Redis DataBase）

在主从复制中，rdb就是备用了！从机上面！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=NDg1ODNiODU5ZjNkNjEwMGE1MmQwMmM5NmQ1NjBjNjRfM3pWSnZ0WTJndFlLMktBRVg2WnJKZVBlalV4N0ZUSldfVG9rZW46Ym94Y25yWEYzZk8zdHhPT2E5cmZaZFNaTkNnXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

在指定的时间间隔内将内存中的数据集快照写入磁盘，也就是行话讲的Snapshot快照，它恢复时是将快 照文件直接读到内存里。

Redis会单独创建（fork）一个子进程来进行持久化，会先将数据写入到一个临时文件中，待持久化过程 都结束了，再用这个临时文件替换上次持久化好的文件。整个过程中，主进程是不进行任何IO操作的。 这就确保了极高的性能。如果需要进行大规模数据的恢复，且对于数据恢复的完整性不是非常敏感，那RDB方式要比AOF方式更加的高效。RDB的缺点是最后一次持久化后的数据可能丢失。我们默认的就是RDB，一般情况下不需要修改这个配置！

有时候在生产环境我们会将这个文件进行备份！

rdb保存的文件是dump.rdb   都是在我们的配置文件中快照中进行配置的！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=ZDFhOTJkYmNhMTNlODE2MGZhYjVkMGQ4OWFjZTFmMzhfZ0dIazJMWDJJcXdhVmdMYUxvS2taM2RIZmQzYm9JZ1pfVG9rZW46Ym94Y25uUHc5Q01Mem8wQnVXMUdwWVpiaHRjXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=NjhlZjY3OWFjMDRmN2EwOGJmZTRjMzM1Y2E1MWM2ZWFfSWxpRHk3WHBzOUs0NVJEeFJlQnljcEpla0x3eFpISEdfVG9rZW46Ym94Y25hcVg1MDRlSHgxODVBQVVtR2s5ajhiXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

1、save的规则满足的情况下，会自动触发rdb规则 2、执行 ﬂushall 命令，也会触发我们的rdb规则！ 3、退出redis，也会产生 rdb 文件！

备份就自动生成一个 dump.rdb

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=NDk3ZjhjODNkNjMxOGQ2NWNmMDU1YjM1YzliYjU0NzJfUzV5aXRDWklBOXJiclZEWGt2eVR5cXRSMTNSTXJUbnJfVG9rZW46Ym94Y250N3dlWHBydlNrMkJaQmY2NFJITFVjXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

1、只需要将rdb文件放在我们redis启动目录就可以，redis启动的时候会自动检查dump.rdb 恢复其中的数据！

2、查看需要存在的位置

##### 优点：

1、适合大规模的数据恢复！

2、对数据的完整性要不高！

##### 缺点：

1、需要一定的时间间隔进程操作！如果redis意外宕机了，这个最后一次修改数据就没有的了！

2、fork进程的时候，会占用一定的内容空间！！

#### AOF（Append Only File）

将我们的所有命令都记录下来，history，恢复的时候就把这个文件全部在执行一遍！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=OWNlOTc4NGYzOGFkOThkMDBjOWMwMWNmYzczZDQzMjRfcXRXQUdseGdPM0RvQXdjdGkxRVkyR29sQ0NmQU9LOWxfVG9rZW46Ym94Y25qdmxSWTJ6RjlGNmRRZXpwQWhoc25mXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

以日志的形式来记录每个写操作，将Redis执行过的所有指令记录下来（读操作不记录），只许追加文件 但不可以改写文件，redis启动之初会读取该文件重新构建数据，换言之，redis重启的话就根据日志文件 的内容将写指令从前到后执行一次以完成数据的恢复工作

Aof保存的是 appendonly.aof 文件

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=ZTYxMzQwNDVhNmZmZWQ0N2E4MThjNDQ4OWE5NjcxNjVfb1JvR0xkM1RjZHJFRVl2cXYzUndhdW53UjQxVnZGVTVfVG9rZW46Ym94Y255UGpNZ1lwSVJkUmJMaXRFWTBjOXZoXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

默认是不开启的，我们需要手动进行配置！我们只需要将 appendonly 改为yes就开启了 aof！ 重启，redis 就可以生效了！

如果这个 aof 文件有错位，这时候   redis 是启动不起来的吗，我们需要修复这个aof文件

redis 给我们提供了一个工具

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=Y2EyMmM2NDA2NTc5Zjk5ZTJjOGM0NDllOTJkOWQzMGNfYTZyQlFMbnFXamZ4ZU5CeWpYb1BTSmFaRWN4MklOcEJfVG9rZW46Ym94Y25hV1lrN1VBQ3JOVDE1a0I5cjN5akhnXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

如果文件正常，重启就可以直接恢复了！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=M2FmMmRmYjQ1M2NiZDJlNTQ2NmRlODE1ZmE2NTYzZmRfSGZGMVROZ1RqNWdrSTVkaTdvc2pST2MxSHhrempnWTJfVG9rZW46Ym94Y255VW55YnNqVDA4UkxhcmpKUVpseE5lXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

aof 默认就是文件的无限追加，文件会越来越大！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=NDBmZjc2NzBhMWE4ZDVlYWJhNGM2Mzk0MmM2ZWRjOTRfZ3dwTk1IR0g2UDM2SmdTUmNCckhTRTc3V0Ywa21kUEVfVG9rZW46Ym94Y25sclc1a2ZxaGkwNDdYTnU5c2lSb3pmXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

如果 aof 文件大于 64m，太大了！ fork一个新的进程来将我们的文件进行重写！

优点：

1、每一次修改都同步，文件的完整会更加好！

2、每秒同步一次，可能会丢失一秒的数据

3、从不同步，效率最高的！ 缺点：

1、相对于数据文件来说，aof远远大于 rdb，修复的速度也比 rdb慢！

2、Aof 运行效率也要比 rdb 慢，所以我们redis默认的配置就是rdb持久化！

##### 扩展：

1、RDB 持久化方式能够在指定的时间间隔内对你的数据进行快照存储

2、AOF 持久化方式记录每次对服务器写的操作，当服务器重启的时候会重新执行这些命令来恢复原始的数据，AOF命令以Redis   协议追加保存每次写的操作到文件末尾，Redis还能对AOF文件进行后台重写，使得AOF文件的体积不至于过大。

3、只做缓存，如果你只希望你的数据在服务器运行的时候存在，你也可以不使用任何持久化

4、同时开启两种持久化方式

在这种情况下，当redis重启的时候会优先载入AOF文件来恢复原始的数据，因为在通常情况下AOF 文件保存的数据集要比RDB文件保存的数据集要完整。

RDB 的数据不实时，同时使用两者时服务器重启也只会找AOF文件，那要不要只使用AOF呢？作者建议不要，因为RDB更适合用于备份数据库（AOF在不断变化不好备份），快速重启，而且不会有AOF可能潜在的Bug，留着作为一个万一的手段。

5、性能建议

因为RDB文件只用作后备用途，建议只在Slave上持久化RDB文件，而且只要15分钟备份一次就够 了，只保留 save 900 1 这条规则。

如果Enable AOF ，好处是在最恶劣情况下也只会丢失不超过两秒数据，启动脚本较简单只load自己的AOF文件就可以了，代价一是带来了持续的IO，二是AOF rewrite 的最后将 rewrite 过程中产生的新数据写到新文件造成的阻塞几乎是不可避免的。只要硬盘许可，应该尽量减少AOF   rewrite 的频率，AOF重写的基础大小默认值64M太小了，可以设到5G以上，默认超过原大小100%大小重 写可以改到适当的数值。

如果不Enable AOF ，仅靠 Master-Slave Repllcation 实现高可用性也可以，能省掉一大笔IO，也减少了rewrite时带来的系统波动。代价是如果Master/Slave 同时倒掉，会丢失十几分钟的数据， 启动脚本也要比较两个 Master/Slave 中的 RDB文件，载入较新的那个，微博就是这种架构。

## Redis发布订阅

Redis 发布订阅(pub/sub)是一种消息通信模式：发送者(pub)发送消息，订阅者(sub)接收消息。微信、微博、关注系统！

Redis 客户端可以订阅任意数量的频道。订阅/发布消息图：

第一个：消息发送者， 第二个：频道    第三个：消息订阅者！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=MzQ0NDk3OTM3ZmVmNjg5ZjQzMTMxYjc2NTMwYzU1MTlfa0N4aGZjRzhxZHF4ekRyemRwVXJGdEVkWGFKMGFhcVNfVG9rZW46Ym94Y25DVFdianpOa01sQjZFcDdwVWI1STZiXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

下图展示了频道 channel1 ， 以及订阅这个频道的三个客户端 —— client2 、 client5 和 client1 之间的关系：

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=NjMwMjUzZGMzNDU4MGFhODk4M2MxZmQ3NjYzOTA5NDZfOXNFZmM1bnd4NXRleHVXVEpwVlRrY01uaEVzdG50VlJfVG9rZW46Ym94Y25ibkZacVVWSzlYYUk5dmRRaXJBWEplXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

当有新消息通过 PUBLISH 命令发送给频道 channel1 时， 这个消息就会被发送给订阅它的三个客户端：

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=YjIwYzgzMmVjOWYxYmFmZTVhMjcxZGZjYTdjOTI4YTFfd0h0MlE0UjF1UmFES2RrYTNWVjBDbkFYOEJUbm1iY0ZfVG9rZW46Ym94Y25ORFZORG1VQjZEclk1RHVYU0FwdmRlXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

这些命令被广泛用于构建即时通信应用，比如网络聊天室(chatroom)和实时广播、实时提醒等。

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=ZWFlMjQwMzAzOTQyNGE4YjBhYTM3NjlmYjBmY2JjYzJfcWg0OE04ZHNLUHNUcEZjZFVDVTFvbHIzdzhzaE1LblhfVG9rZW46Ym94Y25ONVJIcnVLMVp4ZWFwUFNUbWpKQTRlXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

订阅端：

发送端：

Redis是使用C实现的，通过分析 Redis 源码里的 pubsub.c 文件，了解发布和订阅机制的底层实现，籍此加深对 Redis 的理解。

Redis 通过 PUBLISH 、SUBSCRIBE 和 PSUBSCRIBE 等命令实现发布和订阅功能。微信：

通过 SUBSCRIBE 命令订阅某频道后，redis-server 里维护了一个字典，字典的键就是一个个 频道！， 而字典的值则是一个链表，链表中保存了所有订阅这个 channel 的客户端。SUBSCRIBE 命令的关键， 就是将客户端添加到给定 channel 的订阅链表中。

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=OTA1NTVkNzY0MTNmMmU5Y2E2YjA4YzllNzA5ZDg3MTFfdzhBMW8yVkI2VDJCRWhNSDdsSkNuem8xMHRGZTg3NFFfVG9rZW46Ym94Y24yZVNyaHczck5pVWhsdThYNFBFdWJiXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

通过 PUBLISH 命令向订阅者发送消息，redis-server 会使用给定的频道作为键，在它所维护的 channel

字典中查找记录了订阅这个频道的所有客户端的链表，遍历这个链表，将消息发布给所有订阅者。

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=MWQwZjRjZjg1OTkzNmM4ODczODc4NGFhZDY4N2MyZThfdjFQbFRzbGhnVHA5NTNQNXFwaXcwVlBaVmUzVEprdDVfVG9rZW46Ym94Y250MmtwcmNXa2puckN5bkZxQXRodXpjXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

Pub/Sub 从字面上理解就是发布（Publish）与订阅（Subscribe），在Redis中，你可以设定对某一个key值进行消息发布及消息订阅，当一个key值上进行了消息发布后，所有订阅它的客户端都会收到相应 的消息。这一功能最明显的用法就是用作实时消息系统，比如普通的即时聊天，群聊等功能。

##### 使用场景：

1、实时消息系统！

2、事实聊天！（频道当做聊天室，将信息回显给所有人即可！）

3、订阅，关注系统都是可以的！

稍微复杂的场景我们就会使用 消息中间件 MQ （）

## Redis主从复制

#### 概念

主从复制，是指将一台Redis服务器的数据，复制到其他的Redis服务器。前者称为主节点(master/leader)，后者称为从节点(slave/follower)；数据的复制是单向的，只能由主节点到从节点。Master以写为主，Slave 以读为主。

默认情况下，每台Redis服务器都是主节点；

且一个主节点可以有多个从节点(或没有从节点)，但一个从节点只能有一个主节点。（）

##### 主从复制的作用主要包括：

1、数据冗余：主从复制实现了数据的热备份，是持久化之外的一种数据冗余方式。

2、故障恢复：当主节点出现问题时，可以由从节点提供服务，实现快速的故障恢复；实际上是一种服务 的冗余。

3、负载均衡：在主从复制的基础上，配合读写分离，可以由主节点提供写服务，由从节点提供读服务

（即写Redis数据时应用连接主节点，读Redis数据时应用连接从节点），分担服务器负载；尤其是在写 少读多的场景下，通过多个从节点分担读负载，可以大大提高Redis服务器的并发量。

4、高可用（集群）基石：除了上述作用以外，主从复制还是哨兵和集群能够实施的基础，因此说主从复 制是Redis高可用的基础。

一般来说，要将Redis运用于工程项目中，只使用一台Redis是万万不能的（宕机），原因如下：

1、从结构上，单个Redis服务器会发生单点故障，并且一台服务器需要处理所有的请求负载，压力较 大；

2、从容量上，单个Redis服务器内存容量有限，就算一台Redis服务器内存容量为256G，也不能将所有 内存用作Redis存储内存，一般来说，单台Redis最大使用内存不应该超过20G。

电商网站上的商品，一般都是一次上传，无数次浏览的，说专业点也就是"多读少写"。对于这种场景，我们可以使如下这种架构：

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=MDdhYWY1ZTg3MDEzMTJiNGQ1MTQ3YTNhMWYzZGQ4YTlfc0NZdnhocnJwNGQwNjd2NllNQkFoYXR1M05GdXZtWTNfVG9rZW46Ym94Y25qTVNycWk0cVM1Nm1sbFVWUnpaU2xiXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

主从复制，读写分离！ 80% 的情况下都是在进行读操作！减缓服务器的压力！架构中经常使用！ 一主二从！

只要在公司中，主从复制就是必须要使用的，因为在真实的项目中不可能单机使用Redis！

### 环境配置

只配置从库，不用配置主库！

复制3个配置文件，然后修改对应的信息

1、端口

2、pid 名字

3、log文件名字

4、dump.rdb 名字

修改完毕之后，启动我们的3个redis服务器，可以通过进程信息查看！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=YmU2OTY0ZDJlOWVkMzFhMzAwMTRkNmJiMzU0YzNjYThfb3o1YUVQdlAwQ3QwVGp1eXF0ZUI5QzIxTklTYmRLTTJfVG9rZW46Ym94Y25sUFAwd2pBc1NpSVpUbVZnMEJqOEdkXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

### 一主二从

默认情况下，每台Redis服务器都是主节点； 我们一般情况下只用配置从机就好了！ 认老大！ 一主 （79）二从（80，81）

如果两个都配置完了，就是有两个从机的

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=NDU2ODA2MTFiMDI1MTM3ZDM2ZTY3YTkyMzc2NjU2YmZfa3BHYUwyWVp4eHNWUmIwemxCN1Nvb1dBakNaV3pWTWhfVG9rZW46Ym94Y25IMkFIUTFlZ2R4MTd5VHp4Qk93VmtlXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

真实的从主配置应该在配置文件中配置，这样的话是永久的，我们这里使用的是命令，暂时的！

主机可以写，从机不能写只能读！主机中的所有信息和数据，都会自动被从机保存！ 主机写：

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=ZTQ5MWY0ODk3ZTc2NjRhYjA0N2NiN2QyOTM2ODhhYjlfWE1ZUWxrSUVlQ0VSbnNnazFPVm1EaFFBNUJDZWkyQ0xfVG9rZW46Ym94Y25nSURDT1NMZXFMS0dhWmRjbnVqbndjXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

从机只能读取内容！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=ZGUwMDczNmVlZWY2ZDJlOWY2YTFkOGE4ZWExMGYwNjNfU01sVGdyTGdweGFVbWU4UkF4cXR4SVRVOWFNU0dDQUxfVG9rZW46Ym94Y24zb0R6MW01ck11dUJFOFVXM1VlaU5oXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

测试：主机断开连接，从机依旧连接到主机的，但是没有写操作，这个时候，主机如果回来了，从机依 旧可以直接获取到主机写的信息！

如果是使用命令行，来配置的主从，这个时候如果重启了，就会变回主机！只要变为从机，立马就会从 主机中获取值！

Slave 启动成功连接到 master 后会发送一个sync同步命令

Master 接到命令，启动后台的存盘进程，同时收集所有接收到的用于修改数据集命令，在后台进程执行完毕之后，master将传送整个数据文件到slave，并完成一次完全同步。

全量复制：而slave服务在接收到数据库文件数据后，将其存盘并加载到内存中。增量复制：Master 继续将新的所有收集到的修改命令依次传给slave，完成同步

但是只要是重新连接master，一次完全同步（全量复制）将被自动执行！ 我们的数据一定可以在从机中看到！

上一个M链接下一个 S！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=YTYyNDI2ZmQxYWJhODRmZmE1MTFjMjE0MmYzN2YxYmJfckwzTUNmR2t2TnRTbjl6TlVTYnVQZFBUYlRjaXNpT2tfVG9rZW46Ym94Y25hQUNhU1RUTHp0aGs1NGNCeHpIaE9nXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

这时候也可以完成我们的主从复制！

谋朝篡位

如果主机断开了连接，我们可以使用

让自己变成主机！其他的节点就可以手动连

接到最新的这个主节点（手动）！如果这个时候老大修复了，那就重新连接！

### 哨兵模式

（自动选举老大的模式）

主从切换技术的方法是：当主服务器宕机后，需要手动把一台从服务器切换为主服务器，这就需要人工 干预，费事费力，还会造成一段时间内服务不可用。这不是一种推荐的方式，更多时候，我们优先考虑 哨兵模式。Redis从2.8开始正式提供了Sentinel（哨兵） 架构来解决这个问题。

谋朝篡位的自动版，能够后台监控主机是否故障，如果故障了根据投票数自动将从库转换为主库。

哨兵模式是一种特殊的模式，首先Redis提供了哨兵的命令，哨兵是一个独立的进程，作为进程，它会独 立运行。其原理是**哨兵通过发送命令，等待Redis服务器响应，从而监控运行的多个Redis实例。**

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=OWEwMjYyMjlmZWQ0NmI4MmUyMDkwZWQ3MDJhZDFhODVfNUJTUUtVVW1XNWRGd25rb0NWdkFkczM4OVZlVWJxbnlfVG9rZW46Ym94Y25IN2tuVldCVldQZWlrZ0RydUFBRjNjXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

这里的哨兵有两个作用

通过发送命令，让Redis服务器返回监控其运行状态，包括主服务器和从服务器。

当哨兵监测到master宕机，会自动将slave切换成master，然后通过**发布订阅模式**通知其他的从服 务器，修改配置文件，让它们切换主机。

然而一个哨兵进程对Redis服务器进行监控，可能会出现问题，为此，我们可以使用多个哨兵进行监控。 各个哨兵之间还会进行监控，这样就形成了多哨兵模式。

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=YzcwZmFmMGQ1ZDk2ZWIzNjE0YjRhMWU2MDRiMzVjNDdfUWlGWWxwWEFVU3BCVUV6U01sTDFRZ25ReXU5ZVhabFlfVG9rZW46Ym94Y25qeHBwME40TWNpb2xBMGRHdm9aQnJiXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

假设主服务器宕机，哨兵1先检测到这个结果，系统并不会马上进行failover过程，仅仅是哨兵1主观的认 为主服务器不可用，这个现象成为**主观下线**。当后面的哨兵也检测到主服务器不可用，并且数量达到一 定值时，那么哨兵之间就会进行一次投票，投票的结果由一个哨兵发起，进行failover[故障转移]操作。 切换成功后，就会通过发布订阅模式，让各个哨兵把自己监控的从服务器实现切换主机，这个过程称为 **客观下线**。

我们目前的状态是 一主二从！

1、配置哨兵配置文件 sentinel.conf

后面的这个数字1，代表主机挂了，slave投票看让谁接替成为主机，票数最多的，就会成为主机！

2、启动哨兵！

如果Master   节点断开了，这个时候就会从从机中随机选择一个服务器！ （这里面有一个投票算法！）

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=YTdmNTJmMmQ1Mzk1NzEyYWNhYTNkN2Q5YjVkMTIxNDhfQUp5NDhUVzM1WHF0R3M1M2c2aWQ2SGtydjhVZEE1QzdfVG9rZW46Ym94Y25JN0xMZHE2cXVDT0RMU3pJSk9wSXpoXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

哨兵日志！

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=OTRhMzdlMWUyNGEyMGNlZWI3YjcwMGZmYWU1MWNiYWJfQjJJOUdHVWdoRmFWTUJqYU51NVpyM1I0YTVCbW83dUZfVG9rZW46Ym94Y24xV0NabzZvUDE2UFNySmM4andpa1pjXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

如果主机此时回来了，只能归并到新的主机下，当做从机，这就是哨兵模式的规则！

优点：

1、哨兵集群，基于主从复制模式，所有的主从配置优点，它全有

2、 主从可以切换，故障可以转移，系统的可用性就会更好

3、哨兵模式就是主从模式的升级，手动到自动，更加健壮！ 缺点：

1、Redis   不好啊在线扩容的，集群容量一旦到达上限，在线扩容就十分麻烦！

2、实现哨兵模式的配置其实是很麻烦的，里面有很多选择！

sentinel failover-timeout mymaster 180000

# SCRIPTS EXECUTION

#配置当某一事件发生时所需要执行的脚本，可以通过脚本来通知管理员，例如当系统运行不正常时发邮件通知 相关人员。

#对于脚本的运行结果有以下规则：

#若脚本执行后返回1，那么该脚本稍后将会被再次执行，重复次数目前默认为10 #若脚本执行后返回2，或者比2更高的一个返回值，脚本将不会重复执行。

#如果脚本在执行过程中由于收到系统中断信号被终止了，则同返回值为1时的行为相同。

#一个脚本的最大执行时间为60s，如果超过这个时间，脚本将会被一个SIGKILL信号终止，之后重新执行。

#通知型脚本:当sentinel有任何警告级别的事件发生时（比如说redis实例的主观失效和客观失效等等）， 将会去调用这个脚本，这时这个脚本应该通过邮件，SMS等方式去通知系统管理员关于系统不正常运行的信息。调用该脚本时，将传给脚本两个参数，一个是事件的类型，一个是事件的描述。如果sentinel.conf配 置文件中配置了这个脚本路径，那么必须保证这个脚本存在于这个路径，并且是可执行的，否则sentinel无 法正常启动成功。

#通知脚本

# shell编程

# sentinel notification-script `<master-name>` `<script-path>` sentinel notification-script mymaster /var/redis/notify.sh

# 客户端重新配置主节点参数脚本

# 当一个master由于failover而发生改变时，这个脚本将会被调用，通知相关的客户端关于master地址已经发生改变的信息。

# 以下参数将会在调用脚本时传给脚本:

# `<master-name>` `<role>` `<state>` `<from-ip>` `<from-port>` `<to-ip>` `<to-port>` # 目前`<state>`总是“failover”,

# `<role>`是“leader”或者“observer”中的一个。

# 参数 from-ip, from-port, to-ip, to-port是用来和旧的master和新的master(即旧的slave)通信的

# 这个脚本应该是通用的，能被多次调用，不是针对性的。

# sentinel client-reconfig-script `<master-name>` `<script-path>`

sentinel client-reconfig-script mymaster /var/redis/reconfig.sh # 一般都是由运维来配置！

社会目前程序员饱和（初级和中级）、高级程序员重金难求！（提升自己！）

## Redis缓存穿透和雪崩

在这里我们不会详细的区分析解决方案的底层！

Redis缓存的使用，极大的提升了应用程序的性能和效率，特别是数据查询方面。但同时，它也带来了一 些问题。其中，最要害的问题，就是数据的一致性问题，从严格意义上讲，这个问题无解。如果对数据 的一致性要求很高，那么就不能使用缓存。

另外的一些典型问题就是，缓存穿透、缓存雪崩和缓存击穿。目前，业界也都有比较流行的解决方案。

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=YTg0MDc3YTI4YTExMGY1NzY1YTk2ZjM0NjJmOGVhMzFfMjFidHJucFVXUm9rWXlGZUppVmlJMEJjWGdEdWlNNk1fVG9rZW46Ym94Y25UNmFWS2FteDAwVTZmeHFkeWwxdlFlXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

#### 缓存穿透（查不到）

缓存穿透的概念很简单，用户想要查询一个数据，发现redis内存数据库没有，也就是缓存没有命中，于 是向持久层数据库查询。发现也没有，于是本次查询失败。当用户很多的时候，缓存都没有命中（秒     杀！），于是都去请求了持久层数据库。这会给持久层数据库造成很大的压力，这时候就相当于出现了 缓存穿透。

##### 布隆过滤器

布隆过滤器是一种数据结构，对所有可能查询的参数以hash形式存储，在控制层先进行校验，不符合则 丢弃，从而避免了对底层存储系统的查询压力；

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=M2I4Mjg4NzUwYjUyMzkxNGViMjdkNWNhMWNiNmE4NGVfVm4zNEhlNUVhYTRFNXFBQTJob3MxSnpJWExUeFc3eDZfVG9rZW46Ym94Y256QmtkWHF6Tzg5TEU3QWZocXYyOFJmXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

##### 缓存空对象

当存储层不命中后，即使返回的空对象也将其缓存起来，同时会设置一个过期时间，之后再访问这个数 据将会从缓存中获取，保护了后端数据源；

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=ZThiZjNhMGIyZWJjMzE4MzYxYzU2YjhlZDRmNmIxY2FfSWwzM08ycHZ2NHM4RklCaTBPQno4eHdDeTI2Y2k3UWRfVG9rZW46Ym94Y25PN3ZiV2E0YXlrWVZzQjdYdHo2VDRlXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

但是这种方法会存在两个问题：

1、如果空值能够被缓存起来，这就意味着缓存需要更多的空间存储更多的键，因为这当中可能会有很多 的空值的键；

2、即使对空值设置了过期时间，还是会存在缓存层和存储层的数据会有一段时间窗口的不一致，这对于 需要保持一致性的业务会有影响。

#### 缓存击穿（量太大，缓存过期！）

这里需要注意和缓存击穿的区别，缓存击穿，是指一个key非常热点，在不停的扛着大并发，大并发集中 对这一个点进行访问，当这个key在失效的瞬间，持续的大并发就穿破缓存，直接请求数据库，就像在一 个屏障上凿开了一个洞。

当某个key在过期的瞬间，有大量的请求并发访问，这类数据一般是热点数据，由于缓存过期，会同时访 问数据库来查询最新数据，并且回写缓存，会导使数据库瞬间压力过大。

##### 设置热点数据永不过期

从缓存层面来看，没有设置过期时间，所以不会出现热点 key 过期后产生的问题。

##### 加互斥锁

分布式锁：使用分布式锁，保证对于每个key同时只有一个线程去查询后端服务，其他线程没有获得分布 式锁的权限，因此只需要等待即可。这种方式将高并发的压力转移到了分布式锁，因此对分布式锁的考 验很大。

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=OGFhZGY4ZjBkNDFlZTRkZDRiM2ZlYTIxNTY0MDMxNmFfVFU3RWRNb0s4MWxIMDVudE52QVhGbUp2bWFrbDBrUXdfVG9rZW46Ym94Y256TlFpWTI3UkR1ZmZIS1BVcGVISU9mXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

#### 缓存雪崩

缓存雪崩，是指在某一个时间段，缓存集中过期失效。Redis    宕机！

产生雪崩的原因之一，比如在写本文的时候，马上就要到双十二零点，很快就会迎来一波抢购，这波商 品时间比较集中的放入了缓存，假设缓存一个小时。那么到了凌晨一点钟的时候，这批商品的缓存就都 过期了。而对这批商品的访问查询，都落到了数据库上，对于数据库而言，就会产生周期性的压力波   峰。于是所有的请求都会达到存储层，存储层的调用量会暴增，造成存储层也会挂掉的情况。

![](https://rypnbkno8m.feishu.cn/space/api/box/stream/download/asynccode/?code=OWY2ZjllMjNmODExM2FiZmNjZGZjZTQ1ZGE0NWI2MjRfTjBpZks0OHdIMk1scWtoeWhPbXZWV2NvVFVnNER5bk5fVG9rZW46Ym94Y25oS29kYUNKN1RVaWxsYWhxZlUxMU9lXzE2NDgzNzU3Mjc6MTY0ODM3OTMyN19WNA)

其实集中过期，倒不是非常致命，比较致命的缓存雪崩，是缓存服务器某个节点宕机或断网。因为自然 形成的缓存雪崩，一定是在某个时间段集中创建缓存，这个时候，数据库也是可以顶住压力的。无非就 是对数据库产生周期性的压力而已。而缓存服务节点的宕机，对数据库服务器造成的压力是不可预知   的，很有可能瞬间就把数据库压垮。

##### redis高可用

这个思想的含义是，既然redis有可能挂掉，那我多增设几台redis，这样一台挂掉之后其他的还可以继续 工作，其实就是搭建的集群。（异地多活！）

##### 限流降级（在SpringCloud讲解过！）

这个解决方案的思想是，在缓存失效后，通过加锁或者队列来控制读数据库写缓存的线程数量。比如对 某个key只允许一个线程查询数据和写缓存，其他线程等待。

##### 数据预热

数据加热的含义就是在正式部署之前，我先把可能的数据先预先访问一遍，这样部分可能大量访问的数 据就会加载到缓存中。在即将发生大并发访问前手动触发加载缓存不同的key，设置不同的过期时间，让 缓存失效的时间点尽量均匀。

关注公众号：狂神说，每日更新动态！

所有看我视频学习的小伙伴，分享或者写笔记的时候，可以带上我的视频连接，表示尊重！ 后面的课程安排：bilibili免费直播，[https://space.bilibili.com/9525644](https://space.bilibili.com/95256449)

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image001.gif)

|  |
| - |
|  |

狂神聊Redis

学习方式：不是为了面试和工作学习！仅仅是为了兴趣！兴趣才是最好的老师！基本的理论先学习，然后将知识融汇贯通！

#### ![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image003.gif)狂神的Redis课程安排：

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image004.gif)nosql
讲解

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image005.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image005.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image005.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image005.gif)阿里巴巴架构演进nosql数据模型Nosql四大分类CAP

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image006.gif)BASE

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image007.gif)Redis入门

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image008.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image008.gif)Redis安装（Window&Linux服务器）五大基本数据类型

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image009.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image010.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image011.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image012.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image013.gif)![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image014.gif)StringListSetHashZset

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image015.gif)三种特殊数据类型

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image016.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image017.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image018.gif)geohyperloglogbitmap

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image019.gif)Redis配置详解

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image005.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image020.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image021.gif)Redis持久化RDBAOF

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image022.gif)Redis事务操作

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image022.gif)Redis实现订阅发布

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image023.gif)Redis主从复制

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image007.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image007.gif)Redis哨兵模式（现在公司中所有的集群都用哨兵模式）缓存穿透及解决方案

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image022.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image022.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image022.gif)缓存击穿及解决方案缓存雪崩及解决方案基础API之 Jedis详解

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image023.gif)SpringBoot集成 Redis操作

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image023.gif)Redis的实践分析

|  |
| - |
|  |

[]()Nosql概述

## 为什么要用Nosql

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image002.gif)

|  |
| - |
|  |

90年代，一个基本的网站访问量一般不会太大，单个数据库完全足够！那个时候，更多的去使用静态网页
Html~服务器根本没有太大的压力！思考一下，这种情况下：整个网站的瓶颈是什么？

1、数据量如果太大、一个机器放不下了！

2、数据的索引 （B+Tree），一个机器内存也放不下

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image027.gif)3、访问量（读写混合），一个服务器承受不了~

只要你开始出现以上的三种情况之一，那么你就必须要晋级！

|  |
| - |
|  |

网站80%的情况都是在读，每次都要去查询数据库的话就十分的麻烦！所以说我们希望减轻数据的压力，我们可以使用缓存来保证效率！

发展过程： 优化数据结构和索引--> 文件缓存（IO）---> Memcached（当时最热门的技术！）

|  |
| - |
|  |

技术和业务在发展的同时，对人的要求也越来越高！

本质：数据库（读，写）

早些年MyISAM： 表锁，十分影响效率！高并发下就会出现严重的锁问题转战Innodb：行锁

慢慢的就开始使用分库分表来解决写的压力！ MySQL在哪个年代推出
了表分区！这个并没有多少公司使用！

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image032.jpg)MySQL的 集群，很好满足哪个年代的所有需求！

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image033.gif)![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image034.gif)2010--2020    十年之间，世界已经发生了翻天覆地的变化；（定位，也是一种数据，音乐，热榜！）

MySQL等关系型数据库就不够用了！数据量很多，变化很快~！

MySQL有的使用它来村粗一些比较大的文件，博客，图片！数据库表很大，效率就低了！如果有一种数据库来专门处理这种数据,

MySQL压力就变得十分小（研究如何处理这些问题！）大数据的IO压力下，表几乎没法更大！

|  |
| - |
|  |

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image037.jpg)

|  |
| - |
|  |

用户的个人信息，社交网络，地理位置。用户自己产生的数据，用户日志等等爆发式增长！这时候我们就需要使用NoSQL数据库的，Nosql
可以很好的处理以上的情况！

## 什么是NoSQL

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image002.gif)

|  |
| - |
|  |

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image040.gif)NoSQL = NotOnlySQL（不仅仅是SQL）关系型数据库：表格 ，行 ，列

泛指非关系型数据库的，随着web2.0互联网的诞生！传统的关系型数据库很难对付web2.0时代！尤其   是超大规模的高并发的社区！
暴露出来很多难以克服的问题，NoSQL在当今大数据环境下发展的十分迅速，Redis是发展最快的，而且是我们当下必须要掌握的一个技术！

很多的数据类型用户的个人信息，社交网络，地理位置。这些数据类型的存储不需要一个固定的格式！不需要多月的操作就可以横向扩展的
！ Map<String,Object>使用键值对来控制！

|  |
| - |
|  |

解耦！

1、方便扩展（数据之间没有关系，很好扩展！）

2、大数据量高性能（Redis一秒写8万次，读取11万，NoSQL的缓存记录级，是一种细粒度的缓存，性能会比较高！）

3、数据类型是多样型的！（不需要事先设计数据库！随取随用！如果是数据量十分大的表，很多人就无法设计了！）

4、传统 RDBMS和 NoSQL

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image042.gif)

|  |
| - |
|  |

|  |
| - |
|  |

大数据时代的3V：主要是描述问题的

1. 海量Volume
2. 

多样Variety

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image027.gif)实时Velocity

大数据时代的3高：主要是对程序的要求

1. 高并发
2. 

高可扩

高性能

真正在公司中的实践：NoSQL + RDBMS一起使用才是最强的，阿里巴巴的架构演进！技术没有高低之分，就看你如何去使用！（提升内功，思维的提高！）

## 阿里巴巴演进分析

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image045.gif)

思考问题：这么多东西难道都是在一个数据库中的吗?

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image047.jpg)

技术急不得，越是慢慢学，才能越扎实！开源才是技术的王道！

任何一家互联网的公司，都不可能只是简简单单让用户能用就好了！

大量公司做的都是相同的业务；（竞品协议）

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image027.gif)随着这样的竞争，业务是越来越完善，然后对于开发者的要求也是越来越高！

|  |
| - |
|  |

如果你未来相当一个架构师：   没有什么是加一层解决不了的！

|  |
| - |
|  |

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image051.gif)

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image052.gif)要知道，一个简单地网页背后的技术一定不是大家所想的那么简单！大型互联网应用问题：

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image005.gif)数据类型太多了！

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image005.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image005.gif)数据源繁多，经常重构！数据要改造，大面积改造？

解决问题：

|  |
| - |
|  |

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image056.jpg)这里以上都是NoSQL入门概述，不仅能够提高大家的知识，还可以帮助大家了解大厂的工作内容！

## NoSQL的四大分类

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image002.gif)

#### KV键值对：

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image057.gif)新浪：**Redis**

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image057.gif)美团：Redis + Tair

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image008.gif)阿里、百度：Redis + memecache

#### 文档型数据库（bson格式 和json一样）：

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image005.gif) **MongoDB** （一般必须要掌握）

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image058.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image059.gif)MongoDB是一个基于分布式文件存储的数据库，C++编写，主要用来处理大量的文档！MongoDB是一个介于关系型数据库和非关系型数据中中间的产品！MongoDB是非关系型数据库中功能最丰富，最像关系型数据库的！

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image060.gif)ConthDB

#### 列存储数据库

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image061.gif)**HBase**

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image023.gif)分布式文件系统

#### ![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image063.jpg)![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image064.gif)图关系数据库

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image065.gif)他不是存图形，放的是关系，比如：朋友圈社交网络，广告推荐！

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image065.gif) **Neo4j** ，InfoGrid；

|  |
| - |
|  |

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image068.jpg)

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image014.gif)敬畏之心可以使人进步！宇宙！科幻！

活着的意义？    追求幸福（帮助他人，感恩之心），探索未知（努力的学习，不要这个社会抛弃）

|  |
| - |
|  |

[]()Redis入门

## 概述

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image002.gif)

|  |
| - |
|  |

Redis（RemoteDictionaryServer )，即远程字典服务 !

是一个开源的使用ANSI[C语言](https://baike.baidu.com/item/C%E8%AF%AD%E8%A8%80)编写、支持网络、可基于内存亦可持久化的日志型、Key-Value[数据库](https://baike.baidu.com/item/%E6%95%B0%E6%8D%AE%E5%BA%93/103728)，并提供多种语言的API。

|  |
| - |
|  |

redis会周期性的把更新的数据写入磁盘或者把修改操作写入追加的记录文件，并且在此基础上实现了master-slave(主从)同步。

免费和开源！是当下最热门的 NoSQL技术之一！也被人们称之为结构化数据库！

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image072.gif)

1、内存存储、持久化，内存中是断电即失、所以说持久化很重要（rdb、aof）

2、效率高，可以用于高速缓存

3、发布订阅系统

4、地图信息分析

5、计时器、计数器（浏览量！）

6、........

|  |
| - |
|  |

1、多样的数据类型

2、持久化

3、集群

4、事务

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image074.gif)......

|  |
| - |
|  |

1、狂神的公众号：狂神说2、官网：[https://redis.io/](https://redis.io/)

|  |
| - |
|  |

3、中文网：[http://www.redis.cn/](http://www.redis.cn/)4、下载地址：通过官网下载即可！

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image079.jpg)

注意：Wdinow在 Github上下载（停更很久了！）

Redis推荐都是在Linux服务器上搭建的，我们是基于Linux学习！

## Windows安装

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image045.gif)

|  |
| - |
|  |

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image027.gif)1、下载安装包：[https://github.com/dmajkic/redis/releases](https://github.com/dmajkic/redis/releases)2、下载完毕得到压缩包：

3、解压到自己电脑上的环境目录下的就可以的！Redis   十分的小，只有5M

|  |
| - |
|  |

4、开启Redis，双击运行服务即可！

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image085.jpg)

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image086.gif)5、使用redis客户单来来连接redis

|  |
| - |
|  |

记住一句话，Window下使用确实简单，但是Redis    推荐我们使用Linux去开发使用！

|  |
| - |
|  |

## Linux安装

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image024.gif)

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image091.gif)1、下载安装包！

2、解压Redis的安装包！ 程序/opt

|  |
| - |
|  |

3、进入解压后的文件，可以看到我们redis的配置文件

|  |
| - |
|  |

4、基本的环境安装

|  |
| - |
|  |

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image099.gif)![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image100.gif)5、redis的默认安装路径

|  |
| - |
|  |

6、将redis配置文件。复制到我们当前目录下

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image104.jpg)

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image086.gif)7、redis默认不是后台启动的，修改配置文件！

|  |
| - |
|  |

8、启动Redis服务！

|  |
| - |
|  |

9、使用redis-cli进行连接测试！

|  |
| - |
|  |

10、查看redis的进程是否开启！

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image112.jpg)

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image113.gif)11、如何关闭Redis服务呢？

|  |
| - |
|  |

12、再次查看进程是否存在

|  |
| - |
|  |

13、后面我们会使用单机多Redis启动集群测试！

## ![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image118.gif)测试性能

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image024.gif)

**redis-benchmark**是一个压力测试工具！官方自带的性能测试工具！

redis-benchmark命令参数！

图片来自菜鸟教程：

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image120.jpg)

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image121.gif)我们来简单测试下：

|  |
| - |
|  |

如何查看这些分析呢？

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image125.jpg)

## 基础的知识

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image002.gif)

redis默认有16个数据库

|  |
| - |
|  |

默认使用的是第0个

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image074.gif)可以使用 select进行切换数据库！

|  |
| - |
|  |

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image130.gif)清除当前数据库

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image131.gif)清除全部数据库的内容

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image132.gif)

思考：为什么redis是   6379！粉丝效应！（了解一下即可！）

|  |
| - |
|  |

明白Redis是很快的，官方表示，Redis是基于内存操作，CPU不是Redis性能瓶颈，Redis的瓶颈是根据机器的内存和网络带宽，既然可以使用单线程来实现，就使用单线程了！所有就使用了单线程了！

Redis是C语言写的，官方提供的数据为 100000+的QPS，完全不比同样是使用 key-vale的Memecache差！

#### Redis为什么单线程还这么快？

1、误区1：高性能的服务器一定是多线程的？

2、误区2：多线程（CPU上下文会切换！）一定比单线程效率高！先去CPU>内存>硬盘的速度要有所了解！

核心：redis     是将所有的数据全部放在内存中的，所以说使用单线程去操作效率就是最高的，多线程

（CPU上下文会切换：耗时的操作！！！），对于内存系统来说，如果没有上下文切换效率就是最高的！多次读写都是在一个CPU上的，在内存情况下，这个就是最佳的方案！

|  |
| - |
|  |

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image136.gif)五大数据类型

全段翻译：

Redis   是一个开源（BSD许可）的，内存中的数据结构存储系统，它可以用作数据库、缓存和消息中间件MQ。 它支持多种类型的数据结构，如 字符串（strings）， 散列（hashes）， 列表（lists）， 集合

（sets）， 有序集合（sortedsets） 与范围查询， bitmaps， hyperloglogs和 地理空间

（geospatial） 索引半径查询。 Redis内置了 复制（replication），LUA脚本（Luascripting），LRU驱动事件（LRUeviction），事务（transactions） 和不同级别的 磁盘持久化（persistence）， 并通过Redis哨兵（Sentinel）和自动
分区（Cluster）提供高可用性（highavailability）。

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image137.gif)

## Redis-Key

|  |
| - |
|  |

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image138.gif)127.0.0.1:6379>keys*    # 查看所有的key(empty
listorset)

127.0.0.1:6379>setnamekuangshen
#setkeyOK

127.0.0.1:6379>keys*

1) "name"127.0.0.1:6379>setage1OK

127.0.0.1:6379>keys*

"age"

"name"

127.0.0.1:6379>EXISTSname# 判断当前的key是否存在

(integer)1

127.0.0.1:6379>EXISTS
name1

(integer)0

127.0.0.1:6379>movename1# 移除当前的key

(integer)1

127.0.0.1:6379>keys*

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image064.gif)1)"age"

127.0.0.1:6379>setnameqinjiangOK

127.0.0.1:6379>keys*

"age"

2) "name"127.0.0.1:6379> clear127.0.0.1:6379>keys*
3) 

"age"

2) "name"127.0.0.1:6379>getname"qinjiang"

127.0.0.1:6379>EXPIREname10# 设置key的过期时间，单位是秒

(integer)1

127.0.0.1:6379>ttlname# 查看当前key的剩余时间

(integer)4

127.0.0.1:6379>ttlname

(integer)3

127.0.0.1:6379>ttlname

(integer)2

127.0.0.1:6379>ttlname

(integer)1

127.0.0.1:6379>ttlname

(integer)-2

127.0.0.1:6379>getname(nil)

127.0.0.1:6379>typename# 查看当前key的一个类型！

string

127.0.0.1:6379>typeagestring

后面如果遇到不会的命令，可以在官网查看帮助文档！

|  |
| - |
|  |

## String（字符串）

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image024.gif)

90%的 java程序员使用 redis只会使用一个String类型！

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image141.gif)##########################################################################

127.0.0.1:6379>setkey1v1    # 设置值

OK

127.0.0.1:6379>getkey1       # 获得值

"v1"

127.0.0.1:6379>keys*         # 获得所有的key

1)"key1"

127.0.0.1:6379>EXISTS
key1    # 判断某一个key是否存在

(integer)1

127.0.0.1:6379>APPEND
key1"hello"# 追加字符串，如果当前key不存在，就相当于setkey(integer)7

127.0.0.1:6379>getkey1

"v1hello"

127.0.0.1:6379>STRLENkey1# 获取字符串的长度！

(integer)7

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image142.gif)127.0.0.1:6379>APPENDkey1",kaungshen"(integer)17

127.0.0.1:6379>STRLENkey1

(integer)17

127.0.0.1:6379>getkey1

"v1hello,kaungshen"###########################################################################i++

# 步长 i+=

127.0.0.1:6379>setviews0# 初始浏览量为0OK

127.0.0.1:6379>getviews

"0"

127.0.0.1:6379>incrviews# 自增1浏览量变为1(integer)1

127.0.0.1:6379>incrviews

(integer)2

127.0.0.1:6379>getviews

"2"

127.0.0.1:6379>decrviews#自减1     浏览量-1(integer)1

127.0.0.1:6379>decrviews

(integer)0

127.0.0.1:6379>decrviews

(integer)-1

127.0.0.1:6379>getviews

"-1"

127.0.0.1:6379>INCRBYviews10# 可以设置步长，指定增量！

(integer)9

127.0.0.1:6379>INCRBY
views10

(integer)19

127.0.0.1:6379>DECRBYviews5

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image143.gif)(integer)14

##########################################################################

# 字符串范围 range

127.0.0.1:6379>setkey1"hello,kuangshen"# 设置 key1的值

OK

127.0.0.1:6379>getkey1

"hello,kuangshen"

127.0.0.1:6379>GETRANGE
key103    # 截取字符串 [0,3]

"hell"

127.0.0.1:6379>GETRANGE
key10-1   # 获取全部的字符串 和 getkey是一样的"hello,kuangshen"

# 替换！

127.0.0.1:6379>setkey2abcdefg
OK

127.0.0.1:6379>getkey2

"abcdefg"

127.0.0.1:6379>SETRANGEkey21xx# 替换指定位置开始的字符串！

(integer)7

127.0.0.1:6379>getkey2

"axxdefg"##########################################################################

# setex(setwithexpire)    # 设置过期时间

# setnx(setifnotexist)   # 不存在在设置

（在分布式锁中会常常使用！）127.0.0.1:6379>setex
key330"hello"# 设置key3的值为 hello,30秒后过期OK

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image064.gif)127.0.0.1:6379>ttlkey3

(integer)26

127.0.0.1:6379>getkey3

"hello"

127.0.0.1:6379>setnxmykey"redis"   # 如果mykey不存在，创建mykey(integer)1

127.0.0.1:6379>keys*

"key2"

"mykey"

3) "key1"127.0.0.1:6379>ttlkey3(integer)-2

127.0.0.1:6379>setnxmykey
"MongoDB"   #
如果mykey存在，创建失败！

(integer)0

127.0.0.1:6379>getmykey"redis"

##########################################################################

msetmget

127.0.0.1:6379>msetk1v1k2v2k3v3# 同时设置多个值

OK

127.0.0.1:6379>keys*

1)"k1"

2)"k2"

3)"k3"

127.0.0.1:6379>mgetk1k2k3   # 同时获取多个值

1)"v1"

2)"v2"

3)"v3"

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image144.gif)

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image064.gif)数据结构是相同的！

String类似的使用场景：value除了是我们的字符串还可以是我们的数字！

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image060.gif)计数器

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image060.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image060.gif)统计多单位的数量粉丝数

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image145.gif)对象缓存存储！

## List（列表）

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image024.gif)

基本的数据类型，列表

|  |
| - |
|  |

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image148.gif)在redis里面，我们可以把list玩成 ，栈、队列、阻塞队列！所有的list命令都是用l开头的，Redis不区分大小命令

##########################################################################

127.0.0.1:6379>LPUSHlistone# 将一个值或者多个值，插入到列表头部 （左）

(integer)1

127.0.0.1:6379>LPUSHlisttwo

(integer)2

127.0.0.1:6379>LPUSH
listthree(integer)3

127.0.0.1:6379>LRANGElist0-1   # 获取list中值！

1) "three"
2) 

"two"

"one"

127.0.0.1:6379>LRANGElist01   # 通过区间获取具体的值！

1) "three"
2) 

"two"

127.0.0.1:6379>Rpushlistrighr# 将一个值或者多个值，插入到列表位部 （右）

(integer)4

127.0.0.1:6379>LRANGE
list0-1

"three"

"two"

3) "one"
4) 

"righr"

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image064.gif)##########################################################################LPOP

RPOP

127.0.0.1:6379>LRANGE
list0-1

"three"

"two"

"one"

4) "righr"

127.0.0.1:6379>Lpoplist# 移除list的第一个元素

"three"

127.0.0.1:6379>Rpoplist# 移除list的最后一个元素

"righr"

127.0.0.1:6379>LRANGE
list0-1

"two"

2) "one"##########################################################################Lindex

127.0.0.1:6379>LRANGE
list0-1

1) "two"
2) 

"one"

127.0.0.1:6379>lindexlist1# 通过下标获得 list中的某一个值！

"one"

127.0.0.1:6379>lindex
list0"two"

##########################################################################

Llen

127.0.0.1:6379>Lpush
listone(integer)1

127.0.0.1:6379>Lpushlisttwo

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image149.gif)(integer)2

127.0.0.1:6379>Lpush
listthree(integer)3

127.0.0.1:6379>Llenlist# 返回列表的长度

(integer)3

##########################################################################

移除指定的值！取关 uid

Lrem

127.0.0.1:6379>LRANGE
list0-1

"three"

2) "three"
3) 

"two"

"one"

127.0.0.1:6379>lremlist1one# 移除list集合中指定个数的value，精确匹配

(integer)1

127.0.0.1:6379>LRANGE
list0-1

"three"

2) "three"
3) 

"two"

127.0.0.1:6379>lremlist1three

(integer)1

127.0.0.1:6379>LRANGE
list0-1

"three"

"two"

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image027.gif)127.0.0.1:6379>Lpush
listthree(integer)3

127.0.0.1:6379>lremlist2three

(integer)2

127.0.0.1:6379>LRANGE
list0-1

1)"two"

##########################################################################

trim修剪。； list截断!

127.0.0.1:6379>keys
*

(empty
listorset)

127.0.0.1:6379>Rpush
mylist"hello"(integer)1

127.0.0.1:6379>Rpush
mylist"hello1"(integer)2

127.0.0.1:6379>Rpush
mylist"hello2"(integer)3

127.0.0.1:6379>Rpush
mylist"hello3"(integer)4

127.0.0.1:6379>ltrimmylist12   #
通过下标截取指定的长度，这个list已经被改变了，截断了

只剩下截取的元素！

OK

127.0.0.1:6379>LRANGEmylist0-1

1) "hello1"
2) 

"hello2"

##########################################################################

rpoplpush#
移除列表的最后一个元素，将他移动到新的列表中！

127.0.0.1:6379>rpush
mylist"hello"

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image150.gif)(integer)1

127.0.0.1:6379>rpush
mylist"hello1"(integer)2

127.0.0.1:6379>rpush
mylist"hello2"(integer)3

127.0.0.1:6379>rpoplpushmylist
myotherlist# 移除列表的最后一个元素，将他移动到新的

列表中！

"hello2"

127.0.0.1:6379>lrangemylist0-1# 查看原来的列表

1) "hello"
2) 

"hello1"

127.0.0.1:6379>lrangemyotherlist0-1# 查看目标列表中，确实存在改值！

1)"hello2"

##########################################################################

lset将列表中指定下标的值替换为另外一个值，更新操作127.0.0.1:6379>EXISTS
list# 判断这个列表是否存在(integer)0

127.0.0.1:6379>lsetlist0item   #
如果不存在列表我们去更新就会报错

(error) ERR no such key
127.0.0.1:6379>lpush
listvalue1(integer)1

127.0.0.1:6379>LRANGElist00

1)"value1"

127.0.0.1:6379>lsetlist0item   #
如果存在，更新当前下标的值

OK

127.0.0.1:6379>LRANGE
list00

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image151.gif)1)"item"

127.0.0.1:6379>lsetlist1other    # 如果不存在，则会报错！

(error)
ERR index out of range##########################################################################

linsert# 将某个具体的value插入到列把你中某个元素的前面或者后面！

127.0.0.1:6379>Rpush
mylist"hello"(integer)1

127.0.0.1:6379>Rpush
mylist"world"(integer)2

127.0.0.1:6379>LINSERT
mylistbefore"world""other"(integer)3

127.0.0.1:6379>LRANGEmylist0-1

"hello"

"other"

"world"

127.0.0.1:6379>LINSERT
mylistafterworld
new(integer)4

127.0.0.1:6379>LRANGEmylist0-1

"hello"

"other"

"world"

"new"

|  |
| - |
|  |

他实际上是一个链表，beforeNodeafter， left，right都可以插入值如果key不存在，创建新的链表

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image003.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image003.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image004.gif)如果key存在，新增内容

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image015.gif)如果移除了所有值，空链表，也代表不存在！

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image015.gif)在两边插入或者改动值，效率最高！   中间元素，相对来说效率会低一点~

消息排队！消息队列 （LpushRpop），  栈（LpushLpop）！

## Set（集合）

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image002.gif)

set中的值是不能重读的！

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image153.gif)##########################################################################

127.0.0.1:6379>saddmyset"hello"   # set集合中添加匀速(integer)1

127.0.0.1:6379>sadd
myset"kuangshen"(integer)1

127.0.0.1:6379>saddmyset"lovekuangshen"(integer)1

127.0.0.1:6379>SMEMBERSmyset     # 查看指定set的所有值

1) "hello"
2) 

"lovekuangshen"

"kuangshen"

127.0.0.1:6379>SISMEMBER
mysethello    #
判断某一个值是不是在set集合中！

(integer)1

127.0.0.1:6379>SISMEMBERmysetworld

(integer)0

##########################################################################

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image064.gif)127.0.0.1:6379>scardmyset# 获取set集合中的内容元素个数！

(integer)4

##########################################################################

rem

127.0.0.1:6379>sremmysethello# 移除set集合中的指定元素

(integer)1

127.0.0.1:6379>scard
myset

(integer)3

127.0.0.1:6379>SMEMBERS
myset

"lovekuangshen2"

"lovekuangshen"

"kuangshen"

##########################################################################

set无序不重复集合。抽随机！

127.0.0.1:6379>SMEMBERS
myset

"lovekuangshen2"

"lovekuangshen"

3) "kuangshen"

127.0.0.1:6379>SRANDMEMBERmyset

# 随机抽选出一个元素

"kuangshen"

127.0.0.1:6379>SRANDMEMBERmyset

"kuangshen"

127.0.0.1:6379>SRANDMEMBERmyset

"kuangshen"

127.0.0.1:6379>SRANDMEMBERmyset

"kuangshen"

127.0.0.1:6379>SRANDMEMBERmyset2# 随机抽选出指定个数的元素

1) ![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image154.gif)"lovekuangshen"
2) 

"lovekuangshen2"

127.0.0.1:6379>SRANDMEMBERmyset2

"lovekuangshen"

"lovekuangshen2"

127.0.0.1:6379>SRANDMEMBERmyset      # 随机抽选出一个元素

"lovekuangshen2"

##########################################################################

删除定的key，随机删除key！

127.0.0.1:6379>SMEMBERS
myset

1) "lovekuangshen2"
2) 

"lovekuangshen"

"kuangshen"

127.0.0.1:6379>spopmyset# 随机删除一些set集合中的元素！

"lovekuangshen2"127.0.0.1:6379>
spop myset"lovekuangshen"127.0.0.1:6379>SMEMBERSmyset

1)"kuangshen"

##########################################################################

将一个指定的值，移动到另外一个set集合！127.0.0.1:6379>saddmyset"hello"(integer)1

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image052.gif)127.0.0.1:6379>sadd
myset"world"(integer)1

127.0.0.1:6379>sadd
myset"kuangshen"(integer)1

127.0.0.1:6379>sadd
myset2"set2"(integer)1

127.0.0.1:6379>smovemyset
myset2"kuangshen"

# 将一个指定的值，移动到另外一个set集

合！

(integer)1

127.0.0.1:6379>SMEMBERS
myset

"world"

"hello"

127.0.0.1:6379>SMEMBERSmyset2

"kuangshen"

"set2"

##########################################################################

微博，B站，共同关注！(并集)
数字集合类：

- 差集 SDIFF
- 交集
- 并集

127.0.0.1:6379>SDIFFkey1key2    # 差集

1)"b"

2)"a"

127.0.0.1:6379>SINTER
key1key2   #交集    共同好友就可以这样实现

1)"c"

127.0.0.1:6379>SUNION
key1key2   # 并集

1)"b"

2)"c"

3)"e"

4)"a"

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image155.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image156.gif)

微博，A用户将所有关注的人放在一个set集合中！将它的粉丝也放在一个集合中！共同关注，共同爱好，二度好友，推荐好友！（六度分割理论）

## Hash（哈希）

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image002.gif)

Map集合，key-map!    时候这个值是一个map集合！ 本质和String类型没有太大区别，还是一个简单的

key-vlaue！

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image157.gif)setmyhashﬁeldkuangshen

|                                                                                  |
| -------------------------------------------------------------------------------- |
|                                                                                  |
| 127.0.0.1:6379> hset myhash field1 kuangshen # set一个具体 key-vlaue (integer) 1 |
| 127.0.0.1:6379> hget myhash field1 # 获取一个字段值                              |
| "kuangshen"                                                                      |
| 127.0.0.1:6379> hmset myhash field1 hello field2 world	# set多个 key-vlaue OK    |
| 127.0.0.1:6379> hmget myhash field1 field2	# 获取多个字段值                      |

1) "hello"
2) "world"
   127.0.0.1:6379> hgetall myhash	# 获取全部的数据，
3) "field1"
4) "hello"
5) "field2"
6) "world"
   127.0.0.1:6379> hdel myhash field1 # 删除hash指定key字段！对应的value值也就消失了！
   (integer) 1
   127.0.0.1:6379> hgetall myhash
7) "field2"
8) "world" ########################################################################## hlen

127.0.0.1:6379> hmset myhash field1 hello field2 world OK
127.0.0.1:6379> HGETALL myhash

1) "field2"
2) "world"
3) "field1"
4) "hello"
   127.0.0.1:6379> hlen myhash # 获取hash表的字段数量！
   (integer) 2

##########################################################################
127.0.0.1:6379> HEXISTS myhash field1 # 判断hash中指定字段是否存在！
(integer) 1
127.0.0.1:6379> HEXISTS myhash field3 (integer) 0

##########################################################################

# 只获得所有field # 只获得所有value

127.0.0.1:6379> hkeys myhash # 只获得所有field

1) "field2"
2) "field1"
   ](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image158.gif) |

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image159.gif)

hash变更的数据 usernameage,尤其是是用户信息之类的，经常变动的信息！ hash更适合于对象的存储，String更加适合字符串存储！

## Zset（有序集合）

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image045.gif)

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image064.gif)在set的基础上，增加了一个值，setk1v1   zsetk1score1v1

|  |
| - |
|  |

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image161.gif)

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image027.gif)其与的一些API，通过我们的学习吗，你们剩下的如果工作中有需要，这个时候你可以去查查看官方文档！

案例思路：set排序 存储班级成绩表，工资表排序！普通消息，1， 重要消息 2，带权重进行判断！

排行榜应用实现，取TopN测试！

# 三种特殊数据类型

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image024.gif)

## Geospatial地理位置

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image045.gif)

朋友的定位，附近的人，打车距离计算？

Redis的 Geo在Redis3.2版本就推出了！ 这个功能可以推算地理位置的信息，两地之间的距离，方圆几里的人！

可以查询一些测试数据：[http://www.jsons.cn/lngcodeinfo/0706D99C19A781A3/](http://www.jsons.cn/lngcodeinfo/0706D99C19A781A3/)只有 六个命令：

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image052.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image163.jpg)

、

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image164.gif)官方文档：[https://www.redis.net.cn/order/3685.html](https://www.redis.net.cn/order/3685.html)

|  |
| - |
|  |

| 127.0.0.1:6379> | geoadd | china:city | 116.40 | 39.90 | beijing            |           |
| --------------- | ------ | ---------- | ------ | ----- | ------------------ | --------- |
| (integer)1      |        |            |        |       |                    |           |
| 127.0.0.1:6379> | geoadd | china:city | 121.47 | 31.23 | shanghai           |           |
| (integer)1      |        |            |        |       |                    |           |
| 127.0.0.1:6379> | geoadd | china:city | 106.50 | 29.53 | chongqi114.0522.52 | shengzhen |
| (integer)2      |        |            |        |       |                    |           |

|  |
| - |
|  |

获得当前定位：一定是一个坐标值！

|  |
| - |
|  |

|  |   |  1)1)  |  "chongqi"   |
| -------- | -------------- |
|  2)    |  "341.9374"  |
|  2)1)  |  "xian"      |
|  2)    |  "483.8340"  |

|  |
| - |

|  |
| - |

两人之间的距离！单位：

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image022.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image022.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image022.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image061.gif)**m**表示单位为米。**km**表示单位为千米。**mi**表示单位为英里。**ft**表示单位为英尺。

|  |
| - |
|  |

我附近的人？ （获得所有附近的人的地址，定位！）通过半径来查询！获得指定数量的人，200

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image169.gif)![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image170.gif)所有数据应该都录入：china:city   ，才会让结果更加请求！

| 127.0.0.1:6379>GEORADIUS           | china:city | 110 | 30 | 500 | km |                               |
| ---------------------------------- | ---------- | --- | -- | --- | -- | ----------------------------- |
| 1) "chongqi"                       |            |     |    |     |    |                               |
| 2) "xian" 127.0.0.1:6379>GEORADIUS | china:city | 110 | 30 | 500 | km | withdist#显示到中间距离的位置 |

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image171.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image172.gif)![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image155.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image173.gif)

|  |
| - |
|  |

该命令将返回11个字符的Geohash字符串!

## ![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image175.gif)[]()Hyperloglog

|  |
| - |
|  |

A{1,3,5,7,8,7}

B{1，3,5,7,8}

基数（不重复的元素）= 5，可以接受误差！

|  |
| - |
|  |

Redis2.8.9版本就更新了 Hyperloglog数据结构！

RedisHyperloglog基数统计的算法！

优点：占用的内存是固定，2^64不同的元素的技术，只需要废 12KB内存！如果要从内存角度来比较的话 Hyperloglog首选！

#### 网页的 UV（一个人访问一个网站多次，但是还是算作一个人！）

传统的方式， set保存用户的id，然后就可以统计 set中的元素数量作为标准判断 !

这个方式如果保存大量的用户id，就会比较麻烦！我们的目的是为了计数，而不是保存用户id；

0.81%错误率！ 统计UV任务，可以忽略不计的！

|  |
| - |
|  |

如果允许容错，那么一定可以使用 Hyperloglog   ！

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image170.gif)如果不允许容错，就使用 set或者自己的数据类型即可！

## Bitmap

|  |
| - |
|  |

为什么其他教程都不喜欢讲这些？这些在生活中或者开发中，都有十分多的应用场景，学习了，就是就是多一个思路！

技多不压身！

|  |
| - |
|  |

统计用户信息，活跃，不活跃！ 登录 、 未登录！ 打卡，365打卡！ 两个状态的，都可以使用

Bitmaps！

Bitmap位图，数据结构！ 都是操作二进制位来进行记录，就只有0和 1两个状态！

365天=365bit  1字节
=8bit       46个字节左右！

|  |
| - |
|  |

|  |
| - |
|  |

使用bitmap来记录 周一到周日的打卡！周一：1周二：0周三：0周四：1 ......

查看某一天是否有打卡！

|  |
| - |
|  |

统计操作，统计 打卡的天数！

|  |
| - |
|  |

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)事务

Redis事务本质：一组命令的集合！
一个事务中的所有命令都会被序列化，在事务执行过程的中，会按照顺序执行！

一次性、顺序性、排他性！执行一些列的命令！

|  |
| - |
|  |

Redis事务没有没有隔离级别的概念！

所有的命令在事务中，并没有直接被执行！只有发起执行命令的时候才会执行！ExecRedis单条命令式保存原子性的，但是事务不保证原子性！

redis的事务：

开启事务（multi）命令入队（    ）

执行事务（exec）

|  |
| - |
|  |

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image188.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image189.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image190.gif)![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image191.gif)

|  |
| - |
|  |

|  |
| - |
|  |

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image194.gif)

|  |
| - |
|  |

#### 悲观锁：

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)很悲观，认为什么时候都会出问题，无论做什么都会加锁！

#### 乐观锁：

很乐观，认为什么时候都不会出问题，所以不会上锁！
更新数据的时候去判断一下，在此期间是否有人修改过这个数据，

获取version

更新的时候比较 version

|  |
| - |
|  |

正常执行成功！

|  |
| - |
|  |

测试多线程修改值 , 使用watch可以当做redis的乐观锁操作！

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image197.gif)

如果修改失败，获取最新的值就好

|  |
| - |
|  |

# ![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)[]()Jedis

我们要使用 Java来操作 Redis，知其然并知其所以然，授人以渔！
学习不能急躁，慢慢来会很快！

|  |
| - |
|  |

1、导入对应的依赖

|  |
| - |
|  |

2、编码测试：

连接数据库操作命令断开连接！

|  |
| - |
|  |

输出：

|  |
| - |
|  |

## ![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)常用的API

StringListSetHashZset

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image195.gif)

|  |
| - |
|  |

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image191.gif)

[]()SpringBoot整合

SpringBoot操作数据：spring-datajpajdbcmongodbredis！
SpringData也是和 SpringBoot齐名的项目！

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)说明： 在 SpringBoot2.x之后，原来使用的jedis被替换为了 lettuce?

jedis : 采用的直连，多个线程操作的话，是不安全的，如果想要避免不安全的，使用 jedispool连接池！ 更像 BIO
模式

lettuce : 采用netty，实例可以再多个线程中进行共享，不存在线程不安全的情况！可以减少线程数据了，更像 NIO模式

源码分析：

|  |
| - |
|  |

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image205.gif)

|  |
| - |
|  |

1、导入依赖

|  |
| - |
|  |

2、配置连接

|  |
| - |
|  |

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)3、测试！

|  |
| - |
|  |

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image209.jpg)

|  |
| - |
|  |

关于对象的保存：

|  |
| - |
|  |

我们来编写一个自己的 RedisTemplete

|  |
| - |
|  |

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image215.gif)

所有的redis操作，其实对于java开发人员来说，十分的简单，更重要是要去理解redis的思想和每一种数据结构的用处和作用场景！

[]()Redis.conf详解

启动的时候，就通过配置文件来启动！

|  |
| - |
|  |

工作中，一些小小的配置，可以让你脱颖而出！行家有没有，出手就知道

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image217.jpg)

1、配置文件 unit单位 对大小写不敏感！

|  |
| - |
|  |

就是好比我们学习Spring、Improt， include

|  |
| - |
|  |

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image206.gif)![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image221.gif)

|  |
| - |
|  |

持久化， 在规定的时间内，执行了多少次操作，则会持久化到文件 .rdb.aofredis是内存数据库，如果没有持久化，那么数据断电及失！

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image192.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image188.gif)可以在这里设置redis的密码，默认是没有密码！

|  |
| - |
|  |

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image192.gif)

|  |
| - |
|  |

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)具体的配置，我们在 Redis持久化 中去给大家详细详解！

[]()Redis持久化

面试和工作，持久化都是重点！

Redis是内存数据库，如果不将内存中的数据库状态保存到磁盘，那么一旦服务器进程退出，服务器中的数据库状态也会消失。所以 Redis提供了持久化功能！

### RDB（RedisDataBase）

|  |
| - |
|  |

在主从复制中，rdb就是备用了！从机上面！

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image224.jpg)

在指定的时间间隔内将内存中的数据集快照写入磁盘，也就是行话讲的Snapshot快照，它恢复时是将快照文件直接读到内存里。

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)Redis会单独创建（fork）一个子进程来进行持久化，会先将数据写入到一个临时文件中，待持久化过程都结束了，再用这个临时文件替换上次持久化好的文件。整个过程中，主进程是不进行任何IO操作的。这就确保了极高的性能。如果需要进行大规模数据的恢复，且对于数据恢复的完整性不是非常敏感，那RDB方式要比AOF方式更加的高效。RDB的缺点是最后一次持久化后的数据可能丢失。我们默认的就是RDB，一般情况下不需要修改这个配置！

有时候在生产环境我们会将这个文件进行备份！

rdb保存的文件是dump.rdb   都是在我们的配置文件中快照中进行配置的！

|  |
| - |
|  |

|  |
| - |
|  |

1、save的规则满足的情况下，会自动触发rdb规则2、执行 ﬂushall命令，也会触发我们的rdb规则！3、退出redis，也会产生
rdb文件！

备份就自动生成一个 dump.rdb

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image229.jpg)

|  |
| - |
|  |

1、只需要将rdb文件放在我们redis启动目录就可以，redis启动的时候会自动检查dump.rdb恢复其中的数据！

2、查看需要存在的位置

|  |
| - |
|  |

#### 优点：

1、适合大规模的数据恢复！

2、对数据的完整性要不高！

#### 缺点：

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)1、需要一定的时间间隔进程操作！如果redis意外宕机了，这个最后一次修改数据就没有的了！

2、fork进程的时候，会占用一定的内容空间！！

### AOF（AppendOnlyFile）

将我们的所有命令都记录下来，history，恢复的时候就把这个文件全部在执行一遍！

|  |
| - |
|  |

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image232.jpg)

以日志的形式来记录每个写操作，将Redis执行过的所有指令记录下来（读操作不记录），只许追加文件但不可以改写文件，redis启动之初会读取该文件重新构建数据，换言之，redis重启的话就根据日志文件
的内容将写指令从前到后执行一次以完成数据的恢复工作

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)Aof保存的是 appendonly.aof文件

|  |
| - |
|  |

默认是不开启的，我们需要手动进行配置！我们只需要将 appendonly改为yes就开启了 aof！重启，redis就可以生效了！

如果这个 aof文件有错位，这时候   redis是启动不起来的吗，我们需要修复这个aof文件

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image235.gif)redis给我们提供了一个工具

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image237.jpg)

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)如果文件正常，重启就可以直接恢复了！

|  |
| - |
|  |

aof默认就是文件的无限追加，文件会越来越大！

|  |
| - |
|  |

如果 aof文件大于 64m，太大了！ fork一个新的进程来将我们的文件进行重写！

|  |
| - |
|  |

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image242.gif)

优点：

1、每一次修改都同步，文件的完整会更加好！

2、每秒同步一次，可能会丢失一秒的数据

3、从不同步，效率最高的！
缺点：

1、相对于数据文件来说，aof远远大于 rdb，修复的速度也比 rdb慢！

2、Aof运行效率也要比 rdb慢，所以我们redis默认的配置就是rdb持久化！

#### 扩展：

1、RDB持久化方式能够在指定的时间间隔内对你的数据进行快照存储

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)2、AOF持久化方式记录每次对服务器写的操作，当服务器重启的时候会重新执行这些命令来恢复原始的数据，AOF命令以Redis   协议追加保存每次写的操作到文件末尾，Redis还能对AOF文件进行后台重写，使得AOF文件的体积不至于过大。

3、只做缓存，如果你只希望你的数据在服务器运行的时候存在，你也可以不使用任何持久化

4、同时开启两种持久化方式

在这种情况下，当redis重启的时候会优先载入AOF文件来恢复原始的数据，因为在通常情况下AOF文件保存的数据集要比RDB文件保存的数据集要完整。

RDB的数据不实时，同时使用两者时服务器重启也只会找AOF文件，那要不要只使用AOF呢？作者建议不要，因为RDB更适合用于备份数据库（AOF在不断变化不好备份），快速重启，而且不会有AOF可能潜在的Bug，留着作为一个万一的手段。

5、性能建议

因为RDB文件只用作后备用途，建议只在Slave上持久化RDB文件，而且只要15分钟备份一次就够了，只保留 save9001这条规则。

如果EnableAOF，好处是在最恶劣情况下也只会丢失不超过两秒数据，启动脚本较简单只load自己的AOF文件就可以了，代价一是带来了持续的IO，二是AOFrewrite的最后将 rewrite过程中产生的新数据写到新文件造成的阻塞几乎是不可避免的。只要硬盘许可，应该尽量减少AOF   rewrite的频率，AOF重写的基础大小默认值64M太小了，可以设到5G以上，默认超过原大小100%大小重写可以改到适当的数值。

如果不EnableAOF，仅靠 Master-SlaveRepllcation实现高可用性也可以，能省掉一大笔IO，也减少了rewrite时带来的系统波动。代价是如果Master/Slave同时倒掉，会丢失十几分钟的数据， 启动脚本也要比较两个 Master/Slave中的 RDB文件，载入较新的那个，微博就是这种架构。

[]()Redis发布订阅

Redis发布订阅(pub/sub)是一种消息通信模式：发送者(pub)发送消息，订阅者(sub)接收消息。微信、微博、关注系统！

Redis客户端可以订阅任意数量的频道。订阅/发布消息图：

第一个：消息发送者， 第二个：频道    第三个：消息订阅者！

|  |
| - |
|  |

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)下图展示了频道 channel1， 以及订阅这个频道的三个客户端 —— client2、 client5和 client1之间的关系：

|  |
| - |
|  |

当有新消息通过 PUBLISH命令发送给频道 channel1时， 这个消息就会被发送给订阅它的三个客户端：

|  |
| - |
|  |

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image249.gif)

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)这些命令被广泛用于构建即时通信应用，比如网络聊天室(chatroom)和实时广播、实时提醒等。

|  |
| - |
|  |

订阅端：

|  |
| - |
|  |

发送端：

|  |
| - |
|  |

|  |
| - |
|  |

Redis是使用C实现的，通过分析 Redis源码里的 pubsub.c文件，了解发布和订阅机制的底层实现，籍此加深对 Redis的理解。

Redis通过 PUBLISH、SUBSCRIBE和 PSUBSCRIBE等命令实现发布和订阅功能。微信：

通过 SUBSCRIBE命令订阅某频道后，redis-server里维护了一个字典，字典的键就是一个个 频道！，而字典的值则是一个链表，链表中保存了所有订阅这个
channel的客户端。SUBSCRIBE命令的关键，就是将客户端添加到给定 channel的订阅链表中。

|  |
| - |
|  |

通过 PUBLISH命令向订阅者发送消息，redis-server会使用给定的频道作为键，在它所维护的 channel

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)字典中查找记录了订阅这个频道的所有客户端的链表，遍历这个链表，将消息发布给所有订阅者。

|  |
| - |
|  |

Pub/Sub从字面上理解就是发布（Publish）与订阅（Subscribe），在Redis中，你可以设定对某一个key值进行消息发布及消息订阅，当一个key值上进行了消息发布后，所有订阅它的客户端都会收到相应的消息。这一功能最明显的用法就是用作实时消息系统，比如普通的即时聊天，群聊等功能。

#### 使用场景：

1、实时消息系统！

2、事实聊天！（频道当做聊天室，将信息回显给所有人即可！）

3、订阅，关注系统都是可以的！

稍微复杂的场景我们就会使用 消息中间件 MQ（）

[]()Redis主从复制

### 概念

主从复制，是指将一台Redis服务器的数据，复制到其他的Redis服务器。前者称为主节点(master/leader)，后者称为从节点(slave/follower)；数据的复制是单向的，只能由主节点到从节点。Master以写为主，Slave
以读为主。

默认情况下，每台Redis服务器都是主节点；

且一个主节点可以有多个从节点(或没有从节点)，但一个从节点只能有一个主节点。（）

#### 主从复制的作用主要包括：

1、数据冗余：主从复制实现了数据的热备份，是持久化之外的一种数据冗余方式。

2、故障恢复：当主节点出现问题时，可以由从节点提供服务，实现快速的故障恢复；实际上是一种服务的冗余。

3、负载均衡：在主从复制的基础上，配合读写分离，可以由主节点提供写服务，由从节点提供读服务

（即写Redis数据时应用连接主节点，读Redis数据时应用连接从节点），分担服务器负载；尤其是在写少读多的场景下，通过多个从节点分担读负载，可以大大提高Redis服务器的并发量。

4、高可用（集群）基石：除了上述作用以外，主从复制还是哨兵和集群能够实施的基础，因此说主从复制是Redis高可用的基础。

一般来说，要将Redis运用于工程项目中，只使用一台Redis是万万不能的（宕机），原因如下：

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)1、从结构上，单个Redis服务器会发生单点故障，并且一台服务器需要处理所有的请求负载，压力较大；

2、从容量上，单个Redis服务器内存容量有限，就算一台Redis服务器内存容量为256G，也不能将所有内存用作Redis存储内存，一般来说，单台Redis最大使用内存不应该超过20G。

电商网站上的商品，一般都是一次上传，无数次浏览的，说专业点也就是"多读少写"。对于这种场景，我们可以使如下这种架构：

|  |
| - |
|  |

主从复制，读写分离！ 80%的情况下都是在进行读操作！减缓服务器的压力！架构中经常使用！ 一主二从！

只要在公司中，主从复制就是必须要使用的，因为在真实的项目中不可能单机使用Redis！

## 环境配置

只配置从库，不用配置主库！

|  |
| - |
|  |

复制3个配置文件，然后修改对应的信息

1、端口

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)2、pid名字

3、log文件名字

4、dump.rdb名字

修改完毕之后，启动我们的3个redis服务器，可以通过进程信息查看！

|  |
| - |
|  |

## 一主二从

默认情况下，每台Redis服务器都是主节点； 我们一般情况下只用配置从机就好了！认老大！ 一主 （79）二从（80，81）

|  |
| - |
|  |

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image221.gif)

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)如果两个都配置完了，就是有两个从机的

|  |
| - |
|  |

真实的从主配置应该在配置文件中配置，这样的话是永久的，我们这里使用的是命令，暂时的！

|  |
| - |
|  |

主机可以写，从机不能写只能读！主机中的所有信息和数据，都会自动被从机保存！主机写：

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image266.jpg)

从机只能读取内容！

|  |
| - |
|  |

测试：主机断开连接，从机依旧连接到主机的，但是没有写操作，这个时候，主机如果回来了，从机依旧可以直接获取到主机写的信息！

如果是使用命令行，来配置的主从，这个时候如果重启了，就会变回主机！只要变为从机，立马就会从主机中获取值！

|  |
| - |
|  |

Slave启动成功连接到 master后会发送一个sync同步命令

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)Master接到命令，启动后台的存盘进程，同时收集所有接收到的用于修改数据集命令，在后台进程执行完毕之后，master将传送整个数据文件到slave，并完成一次完全同步。

全量复制：而slave服务在接收到数据库文件数据后，将其存盘并加载到内存中。增量复制：Master继续将新的所有收集到的修改命令依次传给slave，完成同步

但是只要是重新连接master，一次完全同步（全量复制）将被自动执行！
我们的数据一定可以在从机中看到！

|  |
| - |
|  |

上一个M链接下一个 S！

|  |
| - |
|  |

这时候也可以完成我们的主从复制！

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image188.gif)

谋朝篡位

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image271.gif)如果主机断开了连接，我们可以使用

让自己变成主机！其他的节点就可以手动连

接到最新的这个主节点（手动）！如果这个时候老大修复了，那就重新连接！

## 哨兵模式

（自动选举老大的模式）

|  |
| - |
|  |

主从切换技术的方法是：当主服务器宕机后，需要手动把一台从服务器切换为主服务器，这就需要人工干预，费事费力，还会造成一段时间内服务不可用。这不是一种推荐的方式，更多时候，我们优先考虑哨兵模式。Redis从2.8开始正式提供了Sentinel（哨兵） 架构来解决这个问题。

谋朝篡位的自动版，能够后台监控主机是否故障，如果故障了根据投票数自动将从库转换为主库。

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)哨兵模式是一种特殊的模式，首先Redis提供了哨兵的命令，哨兵是一个独立的进程，作为进程，它会独立运行。其原理是**哨兵通过发送命令，等待****Redis****服务器响应，从而监控运行的多个****Redis****实例。**

|  |
| - |
|  |

这里的哨兵有两个作用

通过发送命令，让Redis服务器返回监控其运行状态，包括主服务器和从服务器。

当哨兵监测到master宕机，会自动将slave切换成master，然后通过**发布订阅模式**通知其他的从服务器，修改配置文件，让它们切换主机。

然而一个哨兵进程对Redis服务器进行监控，可能会出现问题，为此，我们可以使用多个哨兵进行监控。各个哨兵之间还会进行监控，这样就形成了多哨兵模式。

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image275.jpg)

假设主服务器宕机，哨兵1先检测到这个结果，系统并不会马上进行failover过程，仅仅是哨兵1主观的认为主服务器不可用，这个现象成为 **主观下线** 。当后面的哨兵也检测到主服务器不可用，并且数量达到一定值时，那么哨兵之间就会进行一次投票，投票的结果由一个哨兵发起，进行failover[故障转移]操作。切换成功后，就会通过发布订阅模式，让各个哨兵把自己监控的从服务器实现切换主机，这个过程称为 **客观下线** 。

|  |
| - |
|  |

我们目前的状态是 一主二从！

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)1、配置哨兵配置文件 sentinel.conf

|  |
| - |
|  |

后面的这个数字1，代表主机挂了，slave投票看让谁接替成为主机，票数最多的，就会成为主机！

2、启动哨兵！

|  |
| - |
|  |

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image205.gif)

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)如果Master   节点断开了，这个时候就会从从机中随机选择一个服务器！
（这里面有一个投票算法！）

|  |
| - |
|  |

哨兵日志！

|  |
| - |
|  |

如果主机此时回来了，只能归并到新的主机下，当做从机，这就是哨兵模式的规则！

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image188.gif)

优点：

1、哨兵集群，基于主从复制模式，所有的主从配置优点，它全有

2、 主从可以切换，故障可以转移，系统的可用性就会更好

3、哨兵模式就是主从模式的升级，手动到自动，更加健壮！缺点：

1、Redis   不好啊在线扩容的，集群容量一旦到达上限，在线扩容就十分麻烦！

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image281.gif)2、实现哨兵模式的配置其实是很麻烦的，里面有很多选择！

|  |
| - |
|  |

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image282.gif)sentinelfailover-timeoutmymaster180000

#SCRIPTS
EXECUTION

#配置当某一事件发生时所需要执行的脚本，可以通过脚本来通知管理员，例如当系统运行不正常时发邮件通知相关人员。

#对于脚本的运行结果有以下规则：

#若脚本执行后返回1，那么该脚本稍后将会被再次执行，重复次数目前默认为10
#若脚本执行后返回2，或者比2更高的一个返回值，脚本将不会重复执行。

#如果脚本在执行过程中由于收到系统中断信号被终止了，则同返回值为1时的行为相同。

#一个脚本的最大执行时间为60s，如果超过这个时间，脚本将会被一个SIGKILL信号终止，之后重新执行。

#通知型脚本:当sentinel有任何警告级别的事件发生时（比如说redis实例的主观失效和客观失效等等），将会去调用这个脚本，这时这个脚本应该通过邮件，SMS等方式去通知系统管理员关于系统不正常运行的信息。调用该脚本时，将传给脚本两个参数，一个是事件的类型，一个是事件的描述。如果sentinel.conf配置文件中配置了这个脚本路径，那么必须保证这个脚本存在于这个路径，并且是可执行的，否则sentinel无法正常启动成功。

#通知脚本

# shell编程

#sentinelnotification-script`<master-name><script-path>`sentinelnotification-scriptmymaster/var/redis/notify.sh

# 客户端重新配置主节点参数脚本

# 当一个master由于failover而发生改变时，这个脚本将会被调用，通知相关的客户端关于master地址已经发生改变的信息。

# 以下参数将会在调用脚本时传给脚本:

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)# `<master-name><role>``<state><from-ip>``<from-port><to-ip>``<to-port>`# 目前`<state>`总是“failover”,

# `<role>`是“leader”或者“observer”中的一个。

# 参数

from-ip,from-port,to-ip,to-port是用来和旧的master和新的master(即旧的slave)通信的

# 这个脚本应该是通用的，能被多次调用，不是针对性的。

#sentinelclient-reconfig-script`<master-name><script-path>`

sentinelclient-reconfig-scriptmymaster/var/redis/reconfig.sh# 一般都是由运维来配置！

社会目前程序员饱和（初级和中级）、高级程序员重金难求！（提升自己！）

|  |
| - |
|  |

[]()Redis缓存穿透和雪崩

在这里我们不会详细的区分析解决方案的底层！

Redis缓存的使用，极大的提升了应用程序的性能和效率，特别是数据查询方面。但同时，它也带来了一些问题。其中，最要害的问题，就是数据的一致性问题，从严格意义上讲，这个问题无解。如果对数据的一致性要求很高，那么就不能使用缓存。

另外的一些典型问题就是，缓存穿透、缓存雪崩和缓存击穿。目前，业界也都有比较流行的解决方案。

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image284.jpg)

### 缓存穿透（查不到）

|  |
| - |
|  |

缓存穿透的概念很简单，用户想要查询一个数据，发现redis内存数据库没有，也就是缓存没有命中，于是向持久层数据库查询。发现也没有，于是本次查询失败。当用户很多的时候，缓存都没有命中（秒     杀！），于是都去请求了持久层数据库。这会给持久层数据库造成很大的压力，这时候就相当于出现了缓存穿透。

|  |
| - |
|  |

#### 布隆过滤器

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)布隆过滤器是一种数据结构，对所有可能查询的参数以hash形式存储，在控制层先进行校验，不符合则丢弃，从而避免了对底层存储系统的查询压力；

|  |
| - |
|  |

#### 缓存空对象

当存储层不命中后，即使返回的空对象也将其缓存起来，同时会设置一个过期时间，之后再访问这个数据将会从缓存中获取，保护了后端数据源；

|  |
| - |
|  |

但是这种方法会存在两个问题：

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)1、如果空值能够被缓存起来，这就意味着缓存需要更多的空间存储更多的键，因为这当中可能会有很多的空值的键；

2、即使对空值设置了过期时间，还是会存在缓存层和存储层的数据会有一段时间窗口的不一致，这对于需要保持一致性的业务会有影响。

### 缓存击穿（量太大，缓存过期！）

|  |
| - |
|  |

这里需要注意和缓存击穿的区别，缓存击穿，是指一个key非常热点，在不停的扛着大并发，大并发集中对这一个点进行访问，当这个key在失效的瞬间，持续的大并发就穿破缓存，直接请求数据库，就像在一个屏障上凿开了一个洞。

当某个key在过期的瞬间，有大量的请求并发访问，这类数据一般是热点数据，由于缓存过期，会同时访问数据库来查询最新数据，并且回写缓存，会导使数据库瞬间压力过大。

|  |
| - |
|  |

#### 设置热点数据永不过期

从缓存层面来看，没有设置过期时间，所以不会出现热点 key过期后产生的问题。

#### 加互斥锁

分布式锁：使用分布式锁，保证对于每个key同时只有一个线程去查询后端服务，其他线程没有获得分布式锁的权限，因此只需要等待即可。这种方式将高并发的压力转移到了分布式锁，因此对分布式锁的考验很大。

![](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image290.jpg)

### 缓存雪崩

|  |
| - |
|  |

缓存雪崩，是指在某一个时间段，缓存集中过期失效。Redis    宕机！

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)产生雪崩的原因之一，比如在写本文的时候，马上就要到双十二零点，很快就会迎来一波抢购，这波商品时间比较集中的放入了缓存，假设缓存一个小时。那么到了凌晨一点钟的时候，这批商品的缓存就都过期了。而对这批商品的访问查询，都落到了数据库上，对于数据库而言，就会产生周期性的压力波   峰。于是所有的请求都会达到存储层，存储层的调用量会暴增，造成存储层也会挂掉的情况。

|  |
| - |
|  |

其实集中过期，倒不是非常致命，比较致命的缓存雪崩，是缓存服务器某个节点宕机或断网。因为自然形成的缓存雪崩，一定是在某个时间段集中创建缓存，这个时候，数据库也是可以顶住压力的。无非就是对数据库产生周期性的压力而已。而缓存服务节点的宕机，对数据库服务器造成的压力是不可预知   的，很有可能瞬间就把数据库压垮。

|  |
| - |
|  |

#### redis高可用

这个思想的含义是，既然redis有可能挂掉，那我多增设几台redis，这样一台挂掉之后其他的还可以继续工作，其实就是搭建的集群。（异地多活！）

#### 限流降级（在SpringCloud讲解过！）

这个解决方案的思想是，在缓存失效后，通过加锁或者队列来控制读数据库写缓存的线程数量。比如对某个key只允许一个线程查询数据和写缓存，其他线程等待。

#### 数据预热

数据加热的含义就是在正式部署之前，我先把可能的数据先预先访问一遍，这样部分可能大量访问的数据就会加载到缓存中。在即将发生大并发访问前手动触发加载缓存不同的key，设置不同的过期时间，让
缓存失效的时间点尽量均匀。

|  |
| - |
|  |

关注公众号：狂神说，每日更新动态！

![bilibili：狂神说Java](file:///C:/Users/shuho/AppData/Local/Temp/msohtmlclip1/01/clip_image185.gif)所有看我视频学习的小伙伴，分享或者写笔记的时候，可以带上我的视频连接，表示尊重！后面的课程安排：bilibili免费直播，[https://space.bilibili.com/9525644](https://space.bilibili.com/95256449)

1. 方便扩展（数据之间就没有关系，很好扩展！）
2. 大数据高大数据量高性能（Redis一秒写8万次，读取11万，NoSQL的缓存记录，是一种细粒度的缓存，性能比较高！）
3. 数据库类型是多样型的！（不需要事先设计数据库！随取随用！如果是数据量十分大的表，很多人就无法设计了）
4. 传统RDBMS和NOSQL
   ```java
   传统的RDBMS
   - 结构化组织
   - SQL
   - 数据和关系都存在单独的表中
   - 数据定义语言
   - 严格的一致性
   - 基础的事物
   ```

```java
NOSQL
- 不仅仅是数据
- 没有固定的查询语言
- 键值对存储，列存储，文档存储，图形数据库（社交关系）
- 最终一致性
- CAP定理 和 BASE （异地多活）
- 高性能，高可用，高可扩
- ....
```

### 3V 3高

3V------海量Velume

    多样Variety

    实时[Velocity](https://so.csdn.net/so/search?q=Velocity&spm=1001.2101.3001.7020)

3高-----[高并发](https://so.csdn.net/so/search?q=%E9%AB%98%E5%B9%B6%E5%8F%91&spm=1001.2101.3001.7020)

    高可扩

    高性能

### NoSQL的四大分类

**KV键值对：**

* 新浪：Redis
* 美团：Redis+Tair
* 阿里、百度：Redis+memecache

**文档型数据库（bson格式和json一样）：**

* MongoDB（一般必须要掌握）
* MongoDB是一个基于分布式文件存储的数据库，c++编写，主要用来处理大量的文档！
* MomgoDB是一个介于关系型数据库和非关系型数据库中中间的产品！
* MongoDB是非关系型数据库中功能最丰富、最像关系型数据库的！
* ConthDB

**列存储数据库**

* HBase
* 分布式文件系统

**图关系数据库**

* 它不是存图形，放的是关系，比如：朋友圈社交网络，广告推荐！
* Neo4j、InfoGrid
