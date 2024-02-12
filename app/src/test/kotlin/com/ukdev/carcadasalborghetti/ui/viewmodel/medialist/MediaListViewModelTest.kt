package com.ukdev.carcadasalborghetti.ui.viewmodel.medialist

import android.net.Uri
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.ukdev.carcadasalborghetti.R
import com.ukdev.carcadasalborghetti.core.tools.Logger
import com.ukdev.carcadasalborghetti.domain.model.Media
import com.ukdev.carcadasalborghetti.domain.model.MediaType
import com.ukdev.carcadasalborghetti.domain.model.Operation
import com.ukdev.carcadasalborghetti.domain.usecase.DownloadMediaUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.GetAvailableOperationsUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.GetFavouritesUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.GetMediaListUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.RemoveFromFavouritesUseCase
import com.ukdev.carcadasalborghetti.domain.usecase.SaveToFavouritesUseCase
import com.ukdev.carcadasalborghetti.testtools.stubMedia
import com.ukdev.carcadasalborghetti.testtools.stubMediaList
import com.ukdev.carcadasalborghetti.ui.mapping.toDomain
import com.ukdev.carcadasalborghetti.ui.mapping.toUi
import com.ukdev.carcadasalborghetti.ui.model.MediaListFragmentType
import com.ukdev.carcadasalborghetti.ui.model.UiError
import com.ukdev.carcadasalborghetti.ui.model.UiOperation
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class MediaListViewModelTest {

    private val mockGetMediaListUseCase = mockk<GetMediaListUseCase>()
    private val mockGetFavouritesUseCase = mockk<GetFavouritesUseCase>()
    private val mockSaveToFavouritesUseCase = mockk<SaveToFavouritesUseCase>()
    private val mockRemoveFromFavouritesUseCase = mockk<RemoveFromFavouritesUseCase>()
    private val mockGetAvailableOperationsUseCase = mockk<GetAvailableOperationsUseCase>()
    private val mockDownloadMediaUseCase = mockk<DownloadMediaUseCase>()
    private val mockLogger = mockk<Logger>(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockkStatic(Uri::parse)
        every { Uri.parse(any()) } returns mockk()
    }

    @Test
    fun `with success getMediaList should set correct view states`() = runTest(testDispatcher) {
        // GIVEN
        val mediaList = stubMediaList()
        every { mockGetMediaListUseCase(MediaType.AUDIO) } returns flowOf(mediaList)
        val viewModel = createViewModel(MediaListFragmentType.AUDIO)

        // WHEN
        viewModel.getMediaList(isRefreshing = false)

        // THEN
        viewModel.state.test {
            skipItems(count = 1)
            val loading = MediaListUiState().onLoading()
            assertThat(awaitItem()).isEqualTo(loading)
            val mediaListReceived = loading.onMediaListReceived(mediaList.map { it.toUi() })
            assertThat(awaitItem()).isEqualTo(mediaListReceived)
            val finishedLoading = mediaListReceived.onFinishedLoading()
            assertThat(awaitItem()).isEqualTo(finishedLoading)
        }
    }

    @Test
    fun `when refreshing getMediaList should set correct view states`() = runTest(testDispatcher) {
        // GIVEN
        val mediaList = stubMediaList()
        every { mockGetMediaListUseCase(MediaType.VIDEO) } returns flowOf(mediaList)
        val viewModel = createViewModel(MediaListFragmentType.VIDEO)

        // WHEN
        viewModel.getMediaList(isRefreshing = true)

        // THEN
        viewModel.state.test {
            skipItems(count = 1)
            val refreshing = MediaListUiState().onRefreshing()
            assertThat(awaitItem()).isEqualTo(refreshing)
            val mediaListReceived = refreshing.onMediaListReceived(mediaList.map { it.toUi() })
            assertThat(awaitItem()).isEqualTo(mediaListReceived)
            val finishedLoading = mediaListReceived.onFinishedLoading()
            assertThat(awaitItem()).isEqualTo(finishedLoading)
        }
    }

    @Test
    fun `with success getting favourites getMediaList should set correct view states`() {
        runTest(testDispatcher) {
            // GIVEN
            val mediaList = stubMediaList()
            every { mockGetFavouritesUseCase() } returns flowOf(mediaList)
            val viewModel = createViewModel(MediaListFragmentType.FAVOURITES)

            // WHEN
            viewModel.getMediaList(isRefreshing = false)

            // THEN
            viewModel.state.test {
                skipItems(count = 1)
                val loading = MediaListUiState().onLoading()
                assertThat(awaitItem()).isEqualTo(loading)
                val mediaListReceived = loading.onMediaListReceived(mediaList.map { it.toUi() })
                assertThat(awaitItem()).isEqualTo(mediaListReceived)
                val finishedLoading = mediaListReceived.onFinishedLoading()
                assertThat(awaitItem()).isEqualTo(finishedLoading)
            }
        }
    }

    @Test
    fun `with empty list getMediaList should set correct view states`() = runTest(testDispatcher) {
        // GIVEN
        val mediaList = emptyList<Media>()
        every { mockGetMediaListUseCase(MediaType.AUDIO) } returns flowOf(mediaList)
        val viewModel = createViewModel(MediaListFragmentType.AUDIO)

        // WHEN
        viewModel.getMediaList(isRefreshing = false)

        // THEN
        viewModel.state.test {
            skipItems(count = 1)
            val loading = MediaListUiState().onLoading()
            assertThat(awaitItem()).isEqualTo(loading)
            val error = loading.onError(UiError.UNKNOWN)
            assertThat(awaitItem()).isEqualTo(error)
            val finishedLoading = error.onFinishedLoading()
            assertThat(awaitItem()).isEqualTo(finishedLoading)
        }
    }

    @Test
    fun `with empty list getting favourites getMediaList should set correct view states`() {
        runTest(testDispatcher) {
            // GIVEN
            val mediaList = emptyList<Media>()
            every { mockGetFavouritesUseCase() } returns flowOf(mediaList)
            val viewModel = createViewModel(MediaListFragmentType.FAVOURITES)

            // WHEN
            viewModel.getMediaList(isRefreshing = false)

            // THEN
            viewModel.state.test {
                skipItems(count = 1)
                val loading = MediaListUiState().onLoading()
                assertThat(awaitItem()).isEqualTo(loading)
                val error = loading.onError(UiError.NO_FAVOURITES)
                assertThat(awaitItem()).isEqualTo(error)
                val finishedLoading = error.onFinishedLoading()
                assertThat(awaitItem()).isEqualTo(finishedLoading)
            }
        }
    }

    @Test
    fun `getMediaList should log exception`() = runTest(testDispatcher) {
        // GIVEN
        val exception = IllegalArgumentException()
        every { mockGetMediaListUseCase(MediaType.VIDEO) } returns flow { throw exception }
        val viewModel = createViewModel(MediaListFragmentType.VIDEO)

        // WHEN
        viewModel.getMediaList(isRefreshing = false)

        // THEN
        advanceUntilIdle()
        verify { mockLogger.error(exception) }
    }

    @Test
    fun `with IOException getMediaList should set correct view states`() = runTest(testDispatcher) {
        // GIVEN
        every { mockGetMediaListUseCase(MediaType.VIDEO) } returns flow { throw IOException() }
        val viewModel = createViewModel(MediaListFragmentType.VIDEO)

        // WHEN
        viewModel.getMediaList(isRefreshing = false)

        // THEN
        viewModel.state.test {
            skipItems(count = 1)
            val loading = MediaListUiState().onLoading()
            assertThat(awaitItem()).isEqualTo(loading)
            val error = loading.onError(UiError.CONNECTION)
            assertThat(awaitItem()).isEqualTo(error)
            val finishedLoading = error.onFinishedLoading()
            assertThat(awaitItem()).isEqualTo(finishedLoading)
        }
    }

    @Test
    fun `with exception getMediaList should set correct view states`() = runTest(testDispatcher) {
        // GIVEN
        every { mockGetMediaListUseCase(MediaType.VIDEO) } returns flow { throw Throwable() }
        val viewModel = createViewModel(MediaListFragmentType.VIDEO)

        // WHEN
        viewModel.getMediaList(isRefreshing = false)

        // THEN
        viewModel.state.test {
            skipItems(count = 1)
            val loading = MediaListUiState().onLoading()
            assertThat(awaitItem()).isEqualTo(loading)
            val error = loading.onError(UiError.UNKNOWN)
            assertThat(awaitItem()).isEqualTo(error)
            val finishedLoading = error.onFinishedLoading()
            assertThat(awaitItem()).isEqualTo(finishedLoading)
        }
    }

    @Test
    fun `onItemClicked should log exception`() = runTest(testDispatcher) {
        // GIVEN
        val media = stubMedia(MediaType.AUDIO).toUi()
        val rawMedia = media.toDomain()
        val exception = IOException()
        every { mockDownloadMediaUseCase(rawMedia) } returns flow { throw exception }
        val viewModel = createViewModel(MediaListFragmentType.AUDIO)

        // WHEN
        viewModel.onItemClicked(media)

        // THEN
        advanceUntilIdle()
        verify { mockLogger.error(exception) }
    }

    @Test
    fun `with exception onItemClicked should set correct view state`() = runTest(testDispatcher) {
        // GIVEN
        val media = stubMedia(MediaType.AUDIO).toUi()
        val rawMedia = media.toDomain()
        val exception = IOException()
        every { mockDownloadMediaUseCase(rawMedia) } returns flow { throw exception }
        val viewModel = createViewModel(MediaListFragmentType.AUDIO)

        // WHEN
        viewModel.onItemClicked(media)

        // THEN
        viewModel.state.test {
            skipItems(count = 1)
            val expected = MediaListUiState().onError(UiError.UNKNOWN)
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `onItemClicked should send PlayAudio action`() = runTest(testDispatcher) {
        // GIVEN
        val media = stubMedia(MediaType.AUDIO).toUi()
        val rawMedia = media.toDomain()
        val downloadedMedia = stubMedia(MediaType.AUDIO)
        every { mockDownloadMediaUseCase(rawMedia) } returns flowOf(downloadedMedia)
        val viewModel = createViewModel(MediaListFragmentType.AUDIO)

        // WHEN
        viewModel.onItemClicked(media)

        // THEN
        val expected = MediaListUiAction.PlayAudio(media)
        viewModel.action.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `onItemClicked should send PlayVideo action`() = runTest(testDispatcher) {
        // GIVEN
        val media = stubMedia(MediaType.VIDEO).toUi()
        val rawMedia = media.toDomain()
        val downloadedMedia = stubMedia(MediaType.VIDEO)
        every { mockDownloadMediaUseCase(rawMedia) } returns flowOf(downloadedMedia)
        val viewModel = createViewModel(MediaListFragmentType.VIDEO)

        // WHEN
        viewModel.onItemClicked(media)

        // THEN
        val expected = MediaListUiAction.PlayVideo(media)
        viewModel.action.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `with audio onItemLongClicked should send ShareMedia action`() = runTest(testDispatcher) {
        // GIVEN
        val media = stubMedia().toUi()
        val domainMedia = media.toDomain()
        val operations = listOf(Operation.SHARE)
        every { mockGetAvailableOperationsUseCase(domainMedia) } returns flowOf(operations)
        val downloadedMedia = stubMedia()
        every { mockDownloadMediaUseCase(domainMedia) } returns flowOf(downloadedMedia)
        val viewModel = createViewModel(MediaListFragmentType.AUDIO)

        // WHEN
        viewModel.onItemLongClicked(media)

        // THEN
        val expected = MediaListUiAction.ShareMedia(
            chooserTitleRes = R.string.chooser_title_share,
            chooserSubjectRes = R.string.chooser_subject_share,
            chooserType = "audio/*",
            media = downloadedMedia.toUi()
        )
        viewModel.action.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `with video onItemLongClicked should send ShareMedia action`() = runTest(testDispatcher) {
        // GIVEN
        val media = stubMedia(MediaType.VIDEO).toUi()
        val domainMedia = media.toDomain()
        val operations = listOf(Operation.SHARE)
        every { mockGetAvailableOperationsUseCase(domainMedia) } returns flowOf(operations)
        val downloadedMedia = stubMedia(MediaType.VIDEO)
        every { mockDownloadMediaUseCase(domainMedia) } returns flowOf(downloadedMedia)
        val viewModel = createViewModel(MediaListFragmentType.VIDEO)

        // WHEN
        viewModel.onItemLongClicked(media)

        // THEN
        val expected = MediaListUiAction.ShareMedia(
            chooserTitleRes = R.string.chooser_title_share,
            chooserSubjectRes = R.string.chooser_subject_share,
            chooserType = "video/*",
            media = downloadedMedia.toUi()
        )
        viewModel.action.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `onItemLongClicked should send ShowAvailableOperations`() = runTest(testDispatcher) {
        // GIVEN
        val media = stubMedia(MediaType.VIDEO).toUi()
        val domainMedia = media.toDomain()
        val operations = listOf(Operation.SHARE, Operation.ADD_TO_FAVOURITES)
        every { mockGetAvailableOperationsUseCase(domainMedia) } returns flowOf(operations)
        val downloadedMedia = stubMedia(MediaType.VIDEO)
        every { mockDownloadMediaUseCase(domainMedia) } returns flowOf(downloadedMedia)
        val viewModel = createViewModel(MediaListFragmentType.VIDEO)

        // WHEN
        viewModel.onItemLongClicked(media)

        // THEN
        val uiOperations = operations.map { it.toUi() }
        val uiMedia = downloadedMedia.toUi()
        val expected = MediaListUiAction.ShowAvailableOperations(uiOperations, uiMedia)
        viewModel.action.test {
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `onStopButtonClicked should set correct view state`() = runTest(testDispatcher) {
        // GIVEN
        val viewModel = createViewModel(MediaListFragmentType.AUDIO)

        // WHEN
        viewModel.onStopButtonClicked()

        // THEN
        viewModel.state.test {
            val expected = MediaListUiState().onMediaFinishedPlaying()
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `onStopButtonClicked should send StopAudioPlayback action`() = runTest(testDispatcher) {
        // GIVEN
        val viewModel = createViewModel(MediaListFragmentType.AUDIO)

        // WHEN
        viewModel.onStopButtonClicked()

        // THEN
        viewModel.action.test {
            assertThat(awaitItem()).isEqualTo(MediaListUiAction.StopAudioPlayback)
        }
    }

    @Test
    fun `onPlaybackCompleted should set correct view state`() = runTest(testDispatcher) {
        // GIVEN
        val viewModel = createViewModel(MediaListFragmentType.AUDIO)

        // WHEN
        viewModel.onStopButtonClicked()

        // THEN
        viewModel.state.test {
            val expected = MediaListUiState().onMediaFinishedPlaying()
            assertThat(awaitItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `when adding to favourites onOperationSelected should log exception`() {
        runTest(testDispatcher) {
            // GIVEN
            val media = stubMedia().toUi()
            val domainMedia = media.toDomain()
            val exception = IOException()
            every { mockSaveToFavouritesUseCase(domainMedia) } returns flow { throw exception }
            val viewModel = createViewModel(MediaListFragmentType.AUDIO)

            // WHEN
            viewModel.onOperationSelected(UiOperation.ADD_TO_FAVOURITES, media)

            // THEN
            advanceUntilIdle()
            verify { mockLogger.error(exception) }
        }
    }

    @Test
    fun `when removing from favourites onOperationSelected should log exception`() {
        runTest(testDispatcher) {
            // GIVEN
            val media = stubMedia().toUi()
            val domainMedia = media.toDomain()
            val exception = IOException()
            every { mockRemoveFromFavouritesUseCase(domainMedia) } returns flow { throw exception }
            val viewModel = createViewModel(MediaListFragmentType.AUDIO)

            // WHEN
            viewModel.onOperationSelected(UiOperation.REMOVE_FROM_FAVOURITES, media)

            // THEN
            advanceUntilIdle()
            verify { mockLogger.error(exception) }
        }
    }

    private fun createViewModel(fragmentType: MediaListFragmentType) = MediaListViewModel(
        fragmentType,
        mockGetMediaListUseCase,
        mockGetFavouritesUseCase,
        mockSaveToFavouritesUseCase,
        mockRemoveFromFavouritesUseCase,
        mockGetAvailableOperationsUseCase,
        mockDownloadMediaUseCase,
        mockLogger,
        testDispatcher
    )
}
