##Starting up the Android nGage SDK

1. Get the the latest SDK and extract the zip. Here you will find:

	nGage- android resource project and includes the ngageSDK.jar  library
	SampleApp - Sample SDK project 


2. Add nGage resource to the project: -  Since Android does not allow packing resources directly into a library file you must add the nGage Android project. In eclipse, Import 'nGage' project from the SDK zip file. Go to your apps Project Properties and select Android menu item on left. On the right you will see a 'Library' section. Select the 'Add' button and find the android project 'nGage'. 

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


##nGage SDK Code Integration

Call the **onCreate** function with your app's Activity and your app's AppEngage API Key. You can find your SDK Key on the our dashboard once you have setup a company account and created an app.

```Java
nGage.getInstance().onCreate(this, "YOUR_APP_API_KEY");
```

When your application exits, call function ngage.onDestroy(). Our recommended placement is in your app's Activity onDestroy function but anywhere will do as long as it is when the app exits. 
```Java
nGage.getInstance().onDestroy();
```

##Showing AppEngage Dialog
```Java
nGage.getInstance().showAchievements();
```

##Completing Engagement Actions (Optional)
If you are planning on publishing/advertising using the AppEngage Dialog, you will need to setup and complete engagement actions which you setup in our dashboard.  

To complete an action add the below line when the action requirements are completed in your app. Pass the action type as the parameter.

```Java
nGage.getInstance().completeAction("THE_ACTION");
```

If you app requires a value like High Score as part of the actions requirements then call:
```Java
nGage.getInstance().completeAction(<actionID>,<the_value>);
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
nGage.getInstance().getPendingRewards();
```

Implement the nGageListener to the class you wish to receive your callback on.
```Java
public class myClassWhereIwantReward implements nGageListener
```

Pass that class instance to 'setRewardListener':
```Java
nGage.getInstance().setRewardListener(this);
```

Add the callback function to reward your user:
```Java

@Override
  public void nGageReward(int reward, String currency_claim_token) {
	  // Callback from getPendingRewards call
	  if (reward>0)
		  Log.w("nGage", "You've received a reward of "+reward+". Your server confirmation token is "+ currency_claim_token);
	  
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
```Java
void nGageInterstitial(boolean displayed, String errorCode); 
	@param displayed - If true then the ad was shown and errorCode will be null. If false then no inventory was available or some other server error occurred.
	@param errorCode - errorCode returns a server code prompt for debugging.
```

If you would like to set the device kack key to close the interstitial you can optionally call: 

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
