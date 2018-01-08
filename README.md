# 葫芦娃大作业
## 1, 基本思路
### 参考老师的实例代码，在此基础上进行扩充，并与之前的代码对接
- 将葫芦娃大战蛇精动画看作一场游戏，葫芦娃和蛇精做为游戏双方，据此添加游戏相关类，如服务器server类，玩家player类代表葫芦娃和蛇精等，战场field类。只要玩家存活，则等待一个随机时间后，立即向服务器提交移动和攻击请求，请求的处理以及进程同步问题由服务器统一管理，如生效则由服务器通知战场类重新绘制战场。玩家无权直接操作战场field类。

- 添加Recorder类负责进行游戏过程的记录和回放，服务器的记录功能依赖于此类实现。游戏的每一幅画面作为一帧，立即写入文件。

- 大作业之前的程序已经能够输出葫芦娃与蛇精的各种对战阵型，将此阵型作为一帧画面，启动服务器时载入，作为初始状态。这样即实现了复用之前的代码。

## 2, 程序中关键类的含义及其相互关系
### Game文件夹中的类
- **Class Thing2D**: 描述某个生物（i.e. 葫芦娃、蛇精、小喽喽等）在战场中的位置（x, y）坐标和形象(image)。位置是人为定义的相对偏移，由玩家类继承，与生物类无关。

- **Class Tile**：砖头，继承自Thing2D类。

- **Class Field**: Jpanel的子类，描述战场。主要功能是监听和响应键盘事件，初始化战场，绘制战场状态。与服务器类是聚合关系，与玩家类是关联关系。

- **Class Player**: 玩家类，继承自Thing2D，实现了Runnable，用于描述游戏中玩家的动态。玩家在每个随机间隔后，不停的移动和攻击直到死亡。玩家与服务器以及战场是关联关系。

- **Class Server**: 服务器类，继承自JFrame，用于管理战场状态，记录和回放战况，控制线程同步。服务器类的实现依赖于录像机Recorder类，与战场类是聚合关系，与玩家类是关联关系。
- **其他**：程序中其他文件夹中的类是前几次作业的内容，未做修改，故略去。

## 3, 设计理念
 - **SRP** 单一指责原则，各类应尽量做到高内聚、低耦合，在代码中只负责自己必须完成的事情，不承担额外功能。如服务器类不实现文件IO操作，将细节交由Recorder类完成，其同样不进行具体的战场绘制工作。玩家类只负责移动和攻击，不承担刷新战场任务。

-  **OCP** 开放封闭原则，该原则要求软件实体应该尽量在不修改原有代码的情况下进行扩展。本程序中类的数据大部分均为私有类型，只对外开放必要的接口。

-  **LSP**  里氏替换原则，即子类型必须能够替换掉它们的父类型。

##  4, 部分具体实现
### 4.1 线程同步
```java
/*Server 部分线程同步代码*/
private final byte[] lock = new byte[0];
/*
 * 生成零长度的byte[]对象只需3条操作码, 是一种比较经济的对象锁；
 * 锁住的是代码块，不是方法或者server本身，并发粒度得到提高。
 */
public void waitForMoving() throws InterruptedException{
        synchronized (lock){
            while(!movable) {
                lock.wait();
            }
            movable = false;
        }
    }
```
### 4.2 文件操作
```java
/*用Buffered Streams包装字符流*/
public void writeFrame(String frame){
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(userRecord, true)));
            out.write(frame);
            out.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(out != null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
```

## 4, 其他
- 使用Maven对项目进行管理、添加了单元测试用例；
- 用到了异常处理、集合类型、范型、注解、输入输出等机制；
- 使用了代理，装饰器等设计模式；