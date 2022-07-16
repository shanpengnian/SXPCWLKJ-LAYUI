/**

 @Name：layuiAdmin 公共业务
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License：LPPL

 */

layui.define(function(exports) {
	var $ = layui.$,
		layer = layui.layer,
		laytpl = layui.laytpl,
		setter = layui.setter,
		view = layui.view,
		admin = layui.admin

	//公共业务的逻辑处理可以写在此处，切换任何页面都会执行
	//……
	function getToken() {
		return layui.data(setter.tableName).token;
	}

	//退出
	admin.events.logout = function() {
		//执行退出接口
		obj.get("user/userLogout", {}, function(res) {
			layer.msg(res.msg);
			//清空本地记录的 token，并跳转到登入页
			admin.exit(function() {
				layer.msg("已退出")
				setTimeout(function() {
					location.href = layui.setter.loginUrl;
				}, 1500)


			});
		})

	};
	
	admin.events.openIndexUrl=function(){
		location.href = layui.setter.indexUrl;
	}

	var obj = {
		url: function() {
			return layui.setter.requeryUrl;
		},
		get: function(url, data, callback) {

			url = layui.setter.requeryUrl + url
			$.ajax({
				url: url,
				type: "GET",
				dataType: "json",
				data: data,
				headers: {
					"Access-Control-Allow-Origin": "*",
					"token": getToken()
				},
				success: callback,
				error: function(e) {
					console.log(e)
					if (e.status == 404) {
						layer.msg("登录失效请重新登录!");
						layui.data(setter.tableName, null);
						setTimeout(function() {
							location.href = layui.setter.loginUrl;
						}, 1500)
					} else if (e.status == 403) {
						layer.msg("无权限!");
					} else {
						layer.msg("网络连接失败!");
					}
				},
				complete: function() {

				}
			});
		},
		post: function(url, data, callback) {
			var index = layer.load(2, {
				time: 10 * 1000
			});
			url = layui.setter.requeryUrl + url
			$.ajax({
				url: url,
				type: "POST",
				dataType: "json",
				data: data,
				headers: {
					"Access-Control-Allow-Origin": "*",
					"token": getToken()
				},
				success: callback,
				error: function(e) {
					console.log(e.status)
					if (e.status == 404) {
						layer.msg("登录失效请重新登录!");
						layui.data(setter.tableName, null);
						setTimeout(function() {
							location.href = layui.setter.frontUrl;
						}, 1500)
					} else if (e.status == 403) {
						layer.msg("无权限!");
					} else {
						layer.msg("网络连接失败!");
					}
				},
				complete: function() {
					layer.close(index);
				}
			});
		},
		file: function(upload_ojb, ele, url, callback) {
			//var index = layer.load(2, {time: 10*1000});
			url = layui.setter.requeryUrl + url
			upload_ojb.render({
				url: url,
				headers: {
					'Access-Control-Allow-Origin': "*",
					'token': getToken()
				},
				elem: ele,
				accept: 'file',
				method: 'POST',
				acceptMime: 'image/*',
				done: callback,
				error: function(e) {
					if (e.status == 404) {
						layer.msg("登录失效请重新登录!");
						layui.data(setter.tableName, null);
						setTimeout(function() {
							location.href = layui.setter.frontUrl;
						}, 1500)
					} else if (e.status == 403) {
						layer.msg("无权限!");
					} else {
						layer.msg("网络连接失败!");
					}
				}
			});
		},
		fileMp4: function(upload_ojb, ele, url, callback) {
			//var index = layer.load(2, {time: 10*1000});
			url = layui.setter.requeryUrl + url
			upload_ojb.render({
				url: url,
				headers: {
					'Access-Control-Allow-Origin': "*",
					'token': getToken()
				},
				elem: ele,
				accept: 'file',
				method: 'POST',
				acceptMime: 'video/*',
				done: callback,
				error: function(e) {
					if (e.status == 404) {
						layer.msg("登录失效请重新登录!");
						setTimeout(function() {
							location.href = layui.setter.frontUrl;
						}, 1500)
					} else if (e.status == 403) {
						layer.msg("无权限!");
					} else {
						layer.msg("网络连接失败!");
					}
				}
			});
		},
		randomString: function(len) {
			len = len || 32;
			var $chars =
				'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'; /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
			var maxPos = $chars.length;
			var pwd = '';
			for (i = 0; i < len; i++) {
				pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
			}
			return pwd;
		},
		/*截取code方法*/
		getcode: function(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
			var r = window.location.search.substr(1).match(reg);
			if (r != null) return unescape(r[2]);
			return null;
		}
	};

	//对外暴露的接口
	exports('common', obj);
});
