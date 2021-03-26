# AutoResource
这是一个利用 GitHub Action 自动抓取图片转存至阿里云 oss 的代码仓库。灵感及部分工具类源码来自于[niumoo 的 bing-wallpaper](https://github.com/niumoo/bing-wallpaper)。

## 思路
1. 找到合适资源接口
2. 编写程序访问接口获取图片信息
3. 转存图片至阿里云，备份图片信息
4. 配置 GitHub Action 进行自动化执行 

## 功能
- [X] ~~抓取 [Bing](https://cn.bing.com/) 每日图片~~

- [X] ~~抓取 [Unsplash](https://unsplash.com/) 每日图片~~

- [ ] 抓取极客阅读

- [ ] 抓取知乎日报

- [ ] 抓取微博热点话题

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

### 直接下载
前往 [Bing 图片预览](BingImage.md) 或 [Unsplash 图片预览](UnsplashImage.md)页面选择图片进行下载。

## 更新历史

### v1.0.1

支持从 unsplash 采集图片；

### v1.0

支持从 Bing 获取每日图片；