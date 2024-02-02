import 'package:go_router/go_router.dart';

import '/src/features/chat/presentation/chat_main.dart';

GoRouter addRouter() {
  return GoRouter(routes: <RouteBase>[
    GoRoute(path: '/chat', builder: (context, state) => const ChatMainWidget()),
  ]);
}
