import 'package:eatsplorer/app/presentation/home.dart';
import 'package:eatsplorer/core/theme.dart';
import 'package:flutter/material.dart';

class Eatsplorer extends StatelessWidget {
  const Eatsplorer({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: AppTheme.light(),
      home: const Home(),
    );
  }
}