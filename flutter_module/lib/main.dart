import 'package:flutter/material.dart';
import '/src/features/navigation/service/router.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(const DemoApp());
}

class DemoApp extends StatelessWidget {
  const DemoApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp.router(
      routerConfig: addRouter(),
    );
  }
}
