package io.hefuyi.listener.mvp.usecase;


import android.support.annotation.NonNull;

import java.io.File;

import io.hefuyi.listener.respository.interfaces.Repository;
import io.hefuyi.listener.util.LyricUtil;
import rx.Observable;

/**
 * Created by hefuyi on 2016/11/7.
 */

public class GetLyric extends UseCase<GetLyric.RequestValues, GetLyric.ResponseValue> {

    private Repository mRepository;

    public GetLyric(Repository repository) {
        this.mRepository = repository;
    }

    @Override
    public ResponseValue execute(RequestValues requestValues) {
        String title = requestValues.getTitle();
        String artist = requestValues.getArtist();
        String path = requestValues.getPath();
        long duration = requestValues.getDuration();
        if (LyricUtil.isLrcFileExist(title, artist, path)) {
            return new ResponseValue(LyricUtil.getLocalLyricFile(title, artist,path));
        } else {
            return new ResponseValue(mRepository.downloadLrcFile(title, artist, duration));
        }
    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final String mTitle;
        private final String mArtist;
        private final String mPath;
        private final long mDuration;

        public RequestValues(@NonNull String title, @NonNull String artist, long duration, String path) {
            mTitle = title;
            mArtist = artist;
            mDuration = duration;
            mPath = path;
        }

        public String getTitle() {
            return mTitle;
        }

        public String getArtist() {
            return mArtist;
        }

        public long getDuration() {
            return mDuration;
        }

        public String getPath() {
            return mPath;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final Observable<File> mLyricFile;

        public ResponseValue(Observable<File> lyricFile) {
            mLyricFile = lyricFile;
        }

        public Observable<File> getLyricFile() {
            return mLyricFile;
        }
    }
}
