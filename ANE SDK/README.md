##Setup the ANE nGage SDK

1. Add nGageANE to the projects Native Extensions (remember to check the box for nGageANE for the platform(s) on which you are developing)


2. In your apps Manifest file add the lines inside the <application> tag:
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
nGageANE.getInstance().onCreate(this, "YOUR_APP_API_KEY");
```

When your application exits, call function ngage.onDestroy(). Our recommended placement is in your app's Activity onDestroy function but anywhere will do as long as it is when the app exits. 
```Java
nGageANE.getInstance().onDestroy();
```

##Showing AppEngage Dialog
```Java
nGageANE.getInstance().showAchievements();
```

##Completing Engagement Actions (Optional)
If you are planning on publishing/advertising using the AppEngage Dialog, you will need to setup and complete engagement actions which you setup in our dashboard.  

To complete an action add the below line when the action requirements are completed in your app. Pass the action type as the parameter.

```Java
nGageANE.getInstance().completeAction("THE_ACTION");
```

If you app requires a value like High Score as part of the actions requirements then call:
```Java
nGageANE.getInstance().completeAction(<actionID>,<the_value>);
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
nGageANE.getInstance().getPendingRewards();
```

Add an event listener in the class which you want to handle rewards.  Also you must implement a method to handle this event.
```Java
nGageANE.getInstance().getPendingRewards.addEventListener(nGageANEEvent.REWARD, <YOUR_EVENT_HANDLER_METHOD>);
```
The nGageANEEvent passed to <YOUR_EVENT_HANDLER_METHOD> contains a property "reward" which is the reward amount and "server_token" your server confirmation token. "server_token" is optional in case you wish to server verify rewards. The event handler function will look something like this:

```Java
protected function rewardHandler(event:nGageANEEvent):void
{
	  if (event.reward>0)
		  trace( "You've received a reward of "+ event.reward+". Your server confirmation token is "+ event.server_token);
 
}
```

##Interstitials
If you'd like to show an interstitial call:  

```Java
nGageANE.getInstance().showInterstitial();
```
If you've setup the Receive Rewards section above then you are ready to receive rewards from incentivized interstitial also. 

###Interstitial Fill Callback
You can optionally setup a callback for informational purposes. To do so implement **nGageInterstitialListener** with callback function.
```Java
protected function intersticialHandler(event:nGageInterstitialEvent):void
{
    trace("Intersticial received: " + event.ad_displayed +" "+ event.ad_error_code);	
}
```

Add an event listener in the class which you want to get intersticial information.  

```Java
nGageANE.getInstance().addEventListener(nGageInterstitialEvent.INTERSTITIAL, intersticialHandler);
```

If you would like to set the device kack key to close the interstitial you can optionally call: 

```Java
nGageANE.getInstance().onBackPressed()
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
