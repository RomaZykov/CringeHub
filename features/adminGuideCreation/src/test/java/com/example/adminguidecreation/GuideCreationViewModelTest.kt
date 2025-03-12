package com.example.adminguidecreation

import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class GuideCreationViewModelTest {
//    private lateinit var order: Order
//    private lateinit var repository: FakeCreateNoteRepository
//    private lateinit var addLiveDataWrapper: FakeAddNoteLiveDataWrapper
//    private lateinit var navigation: FakeNavigation
//    private lateinit var clear: FakeClear
//    private lateinit var viewModel: CreateNoteViewModel
//
//    @Before
//    fun setup() {
//        order = Order()
//        clear = FakeClear.Base(order)
//        repository = FakeCreateNoteRepository.Base(order, 101)
//        addLiveDataWrapper = FakeAddNoteLiveDataWrapper.Base(order)
//        navigation = FakeNoteNavigation.Base(order)
//        viewModel = CreateNoteViewModel(
//            addLiveDataWrapper = addLiveDataWrapper,
//            repository = repository,
//            navigation = navigation,
//            clear = clear,
//            dispatcher = Dispatchers.Unconfined,
//            dispatcherMain = Dispatchers.Unconfined
//        )
//    }
//
//    @org.junit.Test
//    fun test_create() {
//        viewModel.createNote(folderId = 4L, text = "new note text")
//
//        repository.check(4L, "new note text")
//        addLiveDataWrapper.check(NoteUi(id = 101, title = "new note text", folderId = 4L))
//        clear.check(listOf(CreateNoteViewModel::class.java))
//        navigation.checkScreen(Screen.Pop)
//        order.check(listOf(CREATE_NOTE_REPOSITORY, NOTE_LIVEDATA_ADD, CLEAR, NAVIGATE))
//    }
}