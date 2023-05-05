
# 阿里云服务器申请Let's Encrypt的泛域名证书

## 摘要

使用certbot进行Let's Encrypt证书申请，certbot官网有申请泛域名证书的教程，但是只支持特定的DNS服务商，其中没有阿里云DNS。\
certbot提供了钩子，这样用户可以自己写脚本，用于调用其他dns服务商的api以增加TXT解析记录。\
网友有写开源的certbot-dns-aliyun脚本，就可以使用certbot的手动模式装入脚本运行完成泛域名证书的申请\
最后设置系统定时任务以定时更新泛域名证书有效期（Let's Encrypt默认申请一次有效期三个月，可重复申请）。

## 准备

云服务器 Ubuntu 20.10\
Nginx\
域名

## 开始

进入[certbot官网](https://certbot.eff.org/)，选择服务器Nginx和系统版本Ubuntu 20，跳转到对应页面。\
选择wildcard选项卡，即泛域名申请教程。

## 安装certbot

Ubuntu 20.10预装了snapd，直接使用

### 更新snapd至最新

`sudo snap install core; sudo snap refresh core`

### 删除原来的certbot

`sudo apt-get remove certbot`

### 用snapd安装

`sudo snap install --classic certbot`

### 配置certbot程序到环境变量中

`sudo ln -s /snap/bin/certbot /usr/bin/certbot`

## 安装阿里云 cli工具

`wget aliyun-cli-linux-latest-amd64.tgz`\
`tar xzvf aliyun-cli-linux-latest-amd64.tgz`\
`sudo cp aliyun /usr/local/bin`\
安装完成后需要配置[凭证信息](https://help.aliyun.com/document_detail/110341.html)

## 获取阿里云DNS api调用脚本

`wget https://cdn.jsdelivr.net/gh/justjavac/certbot-dns-aliyun/alidns.sh`\
`sudo cp alidns.sh /usr/local/bin`\
`sudo ln -s /usr/local/bin/alidns.sh /usr/local/bin/alidns`

## 测试能否安装

`certbot certonly  -d *.example.com --manual --preferred-challenges dns --manual-auth-hook "alidns" --manual-cleanup-hook "alidns clean"`\
替换自己的域名

## 证书续期

`certbot renew --manual --preferred-challenges dns --manual-auth-hook "alidns" --manual-cleanup-hook "alidns clean"`

### 自动续期

`crontab -e`\

#### 输入

`1 1 */1 * * root certbot renew --manual --preferred-challenges dns --manual-auth-hook "alidns" --manual-cleanup-hook "alidns clean" --deploy-hook "nginx -s reload"`
