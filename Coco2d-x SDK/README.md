Cocos2d-x SDK
==============

##Prerequisites
Clone this repo or [download here](https://github.com/PixelAddicts/AppEngage-SDKs/archive/master.zip)

The clone of the repo for Cocos2d-x SDK includes:

	nGageX.cpp/h - Coco2D-x JNI nGage wrapper. 
	nGageHelper.java - The java JNI nGage wrapper that connects to nGageX.cpp/h
	nGageSDK_Vxxx.zip - Latest Android nGage SDK which includes:
		nGage- Android nGage SDK project
		SampleApp - Sample SDK project 

##Starting up the Cocos2d-x Android nGage SDK

1. Lets start by setting up the Android side of the Android nGageSDK project library. Unzip the Android nGageSDK_Vxxx.zip. 

2. Add the nGage SDK to your project. Since Android does not allow packing resources directly into a library file you must add the nGage Android project. In eclipse, Import 'nGage' project from the SDK zip file. Go to your apps Project Properties and select Android menu item on left. On the right you will see a 'Library' section. Select the 'Add' button and find the android project 'nGage'. 

Note: Make sure the nGage project has a Target Android Version of 3.2 or higher. Minimum Android version can be as low as 2.1.

3. In your apps Manifest file add the lines inside the <application> tag:
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

Also in the Manifest, add attribute ```android:launchMode="singleTask"``` to your apps starting activity tag. 
For example, you will have something like ```<activity android:name="com.company.appname.startingActivity" … android:launchMode="singleTask"/>```

4. Let add the Java JNI wrapper. Copy the nGageHelper.java to your Coco2D-x "proj.android/src" folder. Open you activity class and add ```nGageHelper.setActivity(this);``` as described below.
```Java
	//public static Cocos2dxActivity activity;
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		nGageHelper.setActivity(this);
	}
```

5. Copy ```nGageHelper``` to your Android projects src folder. Same folder as the Activity class file.

6. Now let add the Coco JNI wrapper classes. Copy the nGage.cpp and .h wrapper files to you Coco2D-x project. 


##nGage Coco2D-x SDK Code Integration

In your Coco2D-x ```AppDelegate.cpp``` class function ```applicationDidFinishLaunching``` call  **startnGageSession**  and pass it your app's AppEngage API Key. You can find your SDK Key on the our dashboard once you have setup a company account and created an app.

```Java
 nGageX::startnGageSession("<Your apps nGage SDK Key");
```

When your application exits, call function ngage.onDestroy(). Any location will do as long as it is the last call before the app exits. 
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
In your apps Activity **onResume** function add the following code.

```Java
//Calls the server to check for rewards when the app resumes. The placement of this code is crucial to keep your users happy!
nGageX::getPendingRewards();
```

Implement the nGageXDelegate to the class you wish to receive your callback on.
```Java
 nGageXDelegate *callbackReward;
```

Pass that class instance to 'setInterstitialDelegate':
```Java
  nGageX::setDelegate(callbackReward);
```

Add the callback function to reward your user:
```Java
void nGageXDelegate::onReceivenGageReward(int reward,const char* currency_claim_token){
  if (reward>0){
  	CCLog("You've received a reward of %d", reward);
  	CCLog("Your server confirmation token is %s", currency_claim_token);
  }
```

##Interstitials
If you'd like to show an interstitial call:  

```Java
nGage.getInstance().showInterstitial();
```
If you've setup the Receive Rewards section above then you are ready to receive rewards from incentivized interstitial also. 

###Interstitial Fill Callback

You can optionally setup a callback for informational purposes. To do so implement **nGageInterstitialListener** with callback function.

Pass the class instance implementing 'nGageInterstitialListener' to 'setInterstitialListener':
```Java
nGage.getInstance().setInterstitialListener(<classInstance>);
```
Then create the callback function 'nGageInterstitial':

```Java
@Override
void nGageInterstitial(boolean displayed, String errorCode){
	//param displayed - If true then the ad was shown and errorCode will be null. If false then no inventory was available or some other server error occurred.
	//param errorCode - errorCode returns a server code prompt for debugging.
}
```

If you would like to set the device back key to close the interstitial you can optionally call: 

```Java
nGage.getInstance().onBackPressed()
```
which will close the interstitial if it's open. It also returns true if the interstitial was open and was closed successfully. If it returns false the interstitial was not showing and you can process the back key normally for your app. 

##Proguard (optional)
If you are using proguard add the following lines to your proguard.cfg file: 

```Java
-dontwarn com.tinidream.**
-keep class com.tinidream.** {*;}
```

##Sample App
If you have any issues take a look at how the SampleApp works. If you still having issues contact your representative with specific questions and we will be happy to help. 
