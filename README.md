# AutoResource
这是一个利用 GitHub Action 自动抓取图片转存至阿里云 oss 的代码仓库。灵感及部分工具类源码来自于[niumoo 的 bing-wallpaper](https://github.com/niumoo/bing-wallpaper)。

## 思路
1. 找到合适资源接口
2. 编写程序访问接口获取图片信息
3. 转存图片至阿里云，备份图片信息
4. 配置 GitHub Action 进行自动化执行 

## 功能
-[x] 抓取 [Bing](https://cn.bing.com/) 每日图片

-[x] 抓取 [Unsplash](https://unsplash.com/) 每日图片

## 使用
### 自行搭建
1. Fork 本仓库；
2. 注册购买阿里云 oss；
3. 在自己的本仓库副本的 Settings -> Secrets -> New Secrets 依次配置下列内容「注意单次拼写」。
   - endPoint    
     阿里云 oss 地址
   - ossKey    
     阿里云 oss 访问 Key
   - ossSecret    
     阿里云 oss 访问 Secret
   - bucketName    
     阿里云 oss BucketName
   - dir    
     阿里云仓库存储的文件夹
   - actions    
     目前仅支持参数`Bing`，用于抓取 Bing 的每日图片
   - gitToken    
     用于更新仓库所用
     
