import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import '/src/features/navigation/service/router.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(const DemoApp());
}

class DemoApp extends StatelessWidget {
  const DemoApp({super.key});

  @override
  Widget build(BuildContext context) => MaterialApp.router(
      routerConfig: addRouter(),
      theme: Theme.of(context).copyWith(
        appBarTheme: Theme.of(context).appBarTheme.copyWith(
            backgroundColor: Colors.brown,
            iconTheme: const IconThemeData(color: Colors.white),
            systemOverlayStyle: const SystemUiOverlayStyle(statusBarColor: Colors.blue)),
      ));
}
