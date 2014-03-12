Cocos2d-x SDK
==============

##Prerequisites
Clone this repo or [download here](https://github.com/PixelAddicts/AppEngage-SDKs/archive/master.zip)

The clone of the repo for Cocos2d-x SDK includes:

	nGageX.cpp/h - Coco2D-x JNI nGage wrapper
	nGageHelper.java - The java JNI nGage wrapper that connects to nGageX.cpp/h
	nGageSDK_Vxxx.zip - Latest Android nGage SDK
	

##Starting up the Cocos2d-x Android nGage SDK

1 - Lets start by setting up the Android side of the Android nGageSDK project library. Unzip the Android nGageSDK_Vxxx.zip. 

2 - Add the nGage SDK to your project. Since Android does not allow packing resources directly into a library file you must add the nGage Android project. In eclipse, Import 'nGage' project from the SDK zip file. Go to your apps Project Properties and select Android menu item on left. On the right you will see a 'Library' section. Select the 'Add' button and find the android project 'nGage'. 



3 - In your apps Manifest file add the lines inside the <application> tag:

```Java
 <application …>
	…

	 <service android:name="org.openudid.OpenUDID_service">
			 <intent-filter>
				<action android:name="org.openudid.GETUDID"/>
			</intent-filter>
	</service>

      <activity android:screenOrientation="sensorLandscape" android:configChanges="keyboardHidden|orientation" android:name="com.tinidream.ngage.nGageActivity"/>
	…
</application>
``` 

4 - Also in the Manifest, add attribute ```android:launchMode="singleTask"``` to your apps starting activity tag. 
For example, you will have something like ```<activity android:name="com.company.appname.startingActivity" … android:launchMode="singleTask"/>```
```
Note: Make sure the nGage project has a Target Android Version of 3.2 or higher. Minimum Android version can be as low as 2.1.
```

5 - Let add the Java JNI wrapper. Copy the nGageHelper.java to your Coco2D-x "proj.android/src" folder. Open you activity class and add ```nGageHelper.setActivity(this);``` as described below.
```Java
	//public static Cocos2dxActivity activity;
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		nGageHelper.setActivity(this);
	}
```

6 - Copy ```nGageHelper``` to your Android projects src folder. Same folder as the Activity class file.

7 - Now let add the Coco JNI wrapper classes. Copy the nGage.cpp and .h wrapper files to you Coco2D-x project. 



##nGage Coco2D-x SDK Code Integration

In your Coco2D-x ```AppDelegate.cpp``` class function ```applicationDidFinishLaunching``` call  **startnGageSession**  and pass it your app's AppEngage API Key. You can find your SDK Key on the our dashboard once you have setup a company account and created an app.

```Java
 nGageX::startnGageSession("<Your apps nGage SDK Key");
```

When your application exits, call function **nGageX::onDestroy();**. Any location will do as long as it is the last call before the app exits. 
```Java
nGageX::onDestroy(); 
```

##Showing AppEngage Dialog
```Java
nGageX::showAchievementsDialog();
```

##Completing Engagement Actions (Optional)
If you are planning on publishing/advertising using the AppEngage Dialog, you will need to setup and complete engagement actions which you setup in our dashboard.  

To complete an action add the below line when the action requirements are completed in your app. Pass the action type as the parameter.

```Java
nGageX::completeAction(<actionID>);
```

If you app requires a value like High Score as part of the actions requirements then call:
```Java
nGageX::completeAction(<actionID>,<the value>);
```	
	
Built in Engagement Actions:

| Action        | Description   |
| ------------- |:------------- |
| LevelUp      | Called each time your user levels up |
| Win      | Called each time your user wins      |
| Play |  Called each time your user plays a round      |
| Buy | Called each time your user buys an item      |
| Use | Called each time your user uses an item (i.e. power up)     |
| Share | Called each time your user shares on a social network     |

You can also create custom action types on the campaign editor.

##Rewarding Users (Optional)
Don't forget to reward your users with their virtual currency!
Where your apps resumes from an interupt add the following code.

```Java
//Calls the server to check for rewards when the app resumes. The placement of this code is crucial to keep your users happy!
nGageX::getPendingRewards();
```

Create an **nGageXDelegate** instance in the class you wish to receive reward callbacks.
```Java
 nGageXDelegate *callbackReward; //in your h file
```

Pass that class instance to **setDelegate**:
```Java
  callbackReward=new nGageXDelegate();
  nGageX::setDelegate(callbackReward);
```

Add the callback function to reward your user:
```Java
void nGageXDelegate::onReceivenGageReward(int reward,const char* currency_claim_token){
  if (reward>0){
  	CCLog("You've received a reward of %d", reward);
  	CCLog("Your server confirmation token is %s", currency_claim_token);
  }
}
```

##Interstitials
If you'd like to show an interstitial call:  

```Java
nGageX::showInterstitial()
```
If you've setup the Receive Rewards section above then you are ready to receive rewards from incentivized interstitial also. 

###Interstitial Fill Callback

You can optionally setup a callback for informational purposes. To do so implement **nGageXInterstitialDelegate** with callback function.

Create an **nGageXInterstitialDelegate** instance in the class you wish to receive intersticial callbacks.
```Java
nGageXInterstitialDelegate *callbackInterstitial;
```

Pass that class instance to **setInterstitialDelegate**:
```Java
 callbackInterstitial=new nGageXInterstitialDelegate();
 nGageX::setInterstitialDelegate(callbackInterstitial);
```

Add the callback function to reward your user:
```Java
 void nGageXInterstitialDelegate::onReceiveInterstitialInfo(bool displayed,const char* errorCode){
 	CCLog("Did the interstitial display? %d", displayed);
	CCLog("Was there an an error code? %s", errorCode);
 }
```

If you would like to set the device back key to close the interstitial you can optionally call function: 

```Java
 nGageX::onBackPressed();
```
It will close the interstitial if it's open. It also returns true if the interstitial was open and was closed successfully. If it returns false the interstitial was not showing and you can process the back key normally for your app. 

##Proguard (optional)
If you are using proguard add the following lines to your proguard.cfg file: 

```Java
-dontwarn com.tinidream.**
-keep class com.tinidream.** {*;}
```

