/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * aapt tool from the resource data it found.  It
 * should not be modified by hand.
 */

package kr.es.cmschool;

public final class R {
    public static final class attr {
        /** <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
         */
        public static final int buttonBarButtonStyle=0x7f010001;
        /** <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
         */
        public static final int buttonBarStyle=0x7f010000;
    }
    public static final class color {
        public static final int black_overlay=0x7f050000;
    }
    public static final class drawable {
        public static final int ic_launcher=0x7f020000;
        public static final int ic_launcher512=0x7f020001;
    }
    public static final class id {
        public static final int alarm=0x7f09000b;
        public static final int album=0x7f09000e;
        public static final int biblecontents=0x7f090006;
        public static final int contents_line=0x7f090007;
        public static final int fullscreen_content=0x7f090000;
        public static final int fullscreen_content_controls=0x7f090004;
        public static final int index=0x7f090008;
        public static final int letter=0x7f09000d;
        public static final int listView1=0x7f090001;
        public static final int listView2=0x7f090005;
        public static final int login=0x7f09000f;
        public static final int notice=0x7f09000a;
        public static final int scrollView1=0x7f090002;
        public static final int title=0x7f090009;
        public static final int webView1=0x7f090003;
        public static final int week=0x7f09000c;
    }
    public static final class layout {
        public static final int activity_main=0x7f030000;
        public static final int list_item=0x7f030001;
    }
    public static final class menu {
        public static final int menu=0x7f080000;
    }
    public static final class string {
        public static final int alarm_button=0x7f060002;
        public static final int album_button=0x7f060005;
        public static final int app_name=0x7f060000;
        public static final int dummy_content=0x7f06000c;
        public static final int letter_button=0x7f060004;
        public static final int menu_alarm=0x7f060007;
        public static final int menu_album=0x7f06000a;
        public static final int menu_letter=0x7f060009;
        public static final int menu_login=0x7f06000b;
        public static final int menu_notice=0x7f060006;
        public static final int menu_week=0x7f060008;
        public static final int notice_button=0x7f060001;
        public static final int week_button=0x7f060003;
    }
    public static final class style {
        /** 
        Base application theme, dependent on API level. This theme is replaced
        by AppBaseTheme from res/values-vXX/styles.xml on newer devices.
    

            Theme customizations available in newer API levels can go in
            res/values-vXX/styles.xml, while customizations related to
            backward-compatibility can go here.
        

        Base application theme for API 11+. This theme completely replaces
        AppBaseTheme from res/values/styles.xml on API 11+ devices.
    
 API 11 theme customizations can go here. 

        Base application theme for API 14+. This theme completely replaces
        AppBaseTheme from BOTH res/values/styles.xml and
        res/values-v11/styles.xml on API 14+ devices.
    
 API 14 theme customizations can go here. 
         */
        public static final int AppBaseTheme=0x7f070000;
        /**  Application theme. 
 All customizations that are NOT specific to a particular API-level can go here. 
         */
        public static final int AppTheme=0x7f070001;
        public static final int ButtonBar=0x7f070003;
        public static final int ButtonBarButton=0x7f070004;
        public static final int FullscreenActionBarStyle=0x7f070005;
        public static final int FullscreenTheme=0x7f070002;
    }
    public static final class xml {
        public static final int settings=0x7f040000;
    }
    public static final class styleable {
        /** 
         Declare custom theme attributes that allow changing which styles are
         used for button bars depending on the API level.
         ?android:attr/buttonBarStyle is new as of API 11 so this is
         necessary to support previous API levels.
    
           <p>Includes the following attributes:</p>
           <table>
           <colgroup align="left" />
           <colgroup align="left" />
           <tr><th>Attribute</th><th>Description</th></tr>
           <tr><td><code>{@link #ButtonBarContainerTheme_buttonBarButtonStyle kr.es.cmschool:buttonBarButtonStyle}</code></td><td></td></tr>
           <tr><td><code>{@link #ButtonBarContainerTheme_buttonBarStyle kr.es.cmschool:buttonBarStyle}</code></td><td></td></tr>
           </table>
           @see #ButtonBarContainerTheme_buttonBarButtonStyle
           @see #ButtonBarContainerTheme_buttonBarStyle
         */
        public static final int[] ButtonBarContainerTheme = {
            0x7f010000, 0x7f010001
        };
        /**
          <p>This symbol is the offset where the {@link kr.es.cmschool.R.attr#buttonBarButtonStyle}
          attribute's value can be found in the {@link #ButtonBarContainerTheme} array.


          <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
          @attr name kr.es.cmschool:buttonBarButtonStyle
        */
        public static final int ButtonBarContainerTheme_buttonBarButtonStyle = 1;
        /**
          <p>This symbol is the offset where the {@link kr.es.cmschool.R.attr#buttonBarStyle}
          attribute's value can be found in the {@link #ButtonBarContainerTheme} array.


          <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
          @attr name kr.es.cmschool:buttonBarStyle
        */
        public static final int ButtonBarContainerTheme_buttonBarStyle = 0;
    };
}