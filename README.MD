使用本库可以避免onActivityResult,onPermissionResult等等恶心的割裂思维的写法，具体用法参照如下：

## 依赖

## 基本使用

### 申请权限
```
SmoothAtyOperator.startPermission(this)			
                .addPermission(Manifest.permission.WRITE_CONTACTS) //要申请多少个权限就写多少个
                .addPermission(Manifest.permission.CAMERA)
                .build()
                .request(new OnPermissionResultListener() {
                    @Override
                    public void onGranted(String[] permissions) {
                    		//如果权限被全部拒绝，这个方法不会被回调
                        Toast.makeText(MainActivity.this,
                                getString(R.string.app_ask_permission_success,
                                        Arrays.toString(permissions)),
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onDenied(String[] permissions) {
                    		//如果权限被全部授予，这个方法不会被回调
                        Toast.makeText(MainActivity.this,
                                getString(R.string.app_ask_permission_fail,
                                        Arrays.toString(permissions)),
                                Toast.LENGTH_LONG).show();
                    }
                });

```

### Activity跳转
startActivity操作：
```
                SmoothAtyOperator.prepareActivity()
                        //将参数设置到intent中
                        .putExtraToIntent("intent", "test intent")
                        //将参数设置到bundle中
                        .putStringToBundle("bundle", "test bundle")
                        //如果觉得默认的方法不够，可以设置自己想要的intent
                        //.intent(new Intent()) 如果觉得提供
                        //如果觉得默认的方法不够，可以设置自己想要的bundle
                        //.bundle(new Bundle())
                        .build()
                        .startActivity(MainActivity.this, JumpTargetActivity.class);

```

startActivityForResult操作：
```
		SmoothAtyOperator.prepareActivity()
                        .putExtraToIntent("intent", "test intent")
                        .putStringToBundle("bundle", "test bundle")
                        .build()
                        .startActivityForResult(MainActivity.this,
                                JumpTargetActivity.class,
                                new JlActivity.OnActivityResultListener() {
                                    @Override
                                    public void onResultOk(Intent data) {
                                        Toast.makeText(MainActivity.this,
                                                getString(R.string.app_get_return,
                                                        data.getStringExtra("intent")),
                                                Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onResultCancel() {
                                        Toast.makeText(MainActivity.this,
                                                getString(R.string.app_return_cancel),
                                                Toast.LENGTH_LONG).show();
                                    }
                                });
```
可以看到，这里直接在回调中设置返回结果的处理，再也不需要特地跑到onActivityResult方法中取处理了，编写时思维也是一气呵成。