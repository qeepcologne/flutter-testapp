import 'package:android_intent_plus/android_intent.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:platform/platform.dart';

class ChatMainWidget extends StatefulWidget {
  const ChatMainWidget({super.key});

  @override
  State<ChatMainWidget> createState() => _ChatMainWidgetState();
}

class _ChatMainWidgetState extends State<ChatMainWidget> {
  @override
  void initState() {
    super.initState();
  }

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
  }

  @override
  Widget build(BuildContext context) => Scaffold(
        appBar: emptyBar(context, Text("Messages")),
        body: Align(
          alignment: Alignment.topCenter,
          child: emptyChatsBrowseMatches(context)
          ),
        );
}

Widget emptyChatsBrowseMatches(BuildContext context) {
  return Column(children: [
    //browse matches button:
    Align(
        alignment: Alignment.topCenter,
        child: ElevatedButton(
            onPressed: () {
              _fireNativeView(uri: "/matches");
            },
            child: Text("fire native")))
  ]);
}

void _fireNativeView({String? uri, String component = 'com.example.myapplication.MainActivity'}) async {
  if (const LocalPlatform().isAndroid) {
    AndroidIntent intent = AndroidIntent(
        arguments: {'FLUTTER_BACK_TO_NATIVE': true, if (uri != null) 'VIEW_NAME': uri},
        package: "com.example.myapplication",
        componentName: component,
        action: 'action_main');
    await intent.launch();
  }
}

AppBar emptyBar(BuildContext context, Widget title, {List<Widget>? actions, Function()? backPressedHandler}) {
  return AppBar(
//    toolbarHeight: 48,
//    backgroundColor: Colors.red,
//    systemOverlayStyle: const SystemUiOverlayStyle(statusBarColor: Colors.red),
    title: title,
 //   iconTheme: const IconThemeData(color: Colors.white),
    actions: actions ?? [],
  );
}