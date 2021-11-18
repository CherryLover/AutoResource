# AutoResource
这是一个利用 GitHub Action 自动采集信息并进行消息通知、存储的代码仓库。灵感及部分工具类源码来自于[niumoo 的 bing-wallpaper](https://github.com/niumoo/bing-wallpaper)。

## 功能
- [x] ~~抓取 [Bing](https://cn.bing.com/) 每日图片~~
- [x] ~~抓取 [Unsplash](https://unsplash.com/) 每日图片~~
- [x] ~~发送通知到 Telegram~~

## 使用
### 自行搭建
1. Fork 本仓库；

2. 注册购买阿里云 oss；

   如果不想保存到阿里云 oss，可不购买。

3. 修改配置文件 RunConfig.json

4. 在自己的本仓库副本的 Settings -> Secrets -> New Secrets 依次配置下列内容「注意单次拼写」。
   - OSS_KEY
     阿里云 oss 访问 Key，[去查看](https://ram.console.aliyun.com/manage/ak)。
     
   - OSS_SECRET
     
     阿里云 oss 访问 Secret，[去查看](https://ram.console.aliyun.com/manage/ak)。
     
   - UNSPLASH_CLIENT_ID

     Unsplash 开发者 id，[去获取](https://unsplash.com/oauth/applications)。

   - MY_GIT_TOKEN

     用于更新仓库所用，[去生成](https://github.com/settings/tokens/new?description=AutoResourceActionGitCommitToken&scopes=repo%2Cgist%2Cread%3Aorg)。
   
   - TEL_CHAT_ID
     Telegram 私聊对象 id
     
   - TEL_CHAT_TOKEN
     Telegram 机器人 Token
     
   - SERVER_HOST
   Telegram 消息回调服务

## 更新历史

### v1.0.2

支持发送 Telegram 通知消息

### v1.0.1

支持从 unsplash 采集图片；

### v1.0

支持从 Bing 获取每日图片；