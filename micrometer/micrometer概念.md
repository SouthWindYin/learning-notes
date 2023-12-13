# micrometer概念

类似于slf4j，micrometer提供了一套观测、监控jvm应用的包装接口，它可以对接到多种监控系统中。

## Meter 表

micrometer就是为管理一系列的meter而生的，meter有很多种类，包括*Timer, Counter, Gauge, DistributionSummary, LongTaskTimer, FunctionCounter, FunctionTimer, TimeGauge*，meter是由registry生成和管理的

## Registry 登记处

创建和管理所有meter，每个监控系统有自己的MeterRegistry接口实现。micrometer提供SimpleMeterRegistry、CompositeMeterRegistry。一个CompositeMeterRegistry可以包含多个MeterRegistry，因此micrometer提供了同时发布到多个监控系统的功能。

## Tag 标签

meter创建时的一些提示性标签。tag分为meter的tag和registry的common tag。

## Measurement 取样值

每个meter包含多个measurement，是具体的监控的数值

## Meter种类

### Gauge 仪表

一般用于记录某个有界的值，可以是任意的数字（Number的子类）。可以类比为汽车仪表盘，只能展示有界的数值

### Counter 计数器

用于记录无界的值，比如请求数、用户数等。它有一个increment()方法，自增，很常用。

### Timer 计时器

用于记录某一段时间，比如某个程序运行时间

### DistributionSummary 分布概要

用于记录事件的分布情况，表示一段时间范围内对数据进行采样，可以用于统计网络请求平均时延