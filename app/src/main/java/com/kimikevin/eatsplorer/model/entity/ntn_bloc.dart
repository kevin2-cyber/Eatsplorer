import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';

part 'ntn_event.dart';
part 'ntn_state.dart';

class NtnBloc extends Bloc<NtnEvent, NtnState> {
  NtnBloc() : super(NtnInitial()) {
    on<NtnEvent>((event, emit) {
      // TODO: implement event handler
    });
  }
}
