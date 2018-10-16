package com.sirit.android.support.media.demo

import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.sirit.android.support.extention.dp2px
import com.sirit.android.support.lib.compat.StatusBarCompat
import com.sirit.android.support.lib.media.MediaBean
import com.sirit.android.support.lib.media.MediaDataCallback
import com.sirit.android.support.lib.media.MediaHelp
import com.sirit.android.support.media.R

/**
 * @author kai.w
 * @des  $des
 */
class GalleryActivity : AppCompatActivity(), MediaDataCallback {

    private val mediaModel by lazy { intent.getSerializableExtra(Params.MEDIA_MODEL) as? MediaModel? }
    private val mRvGallery by lazy { findViewById<RecyclerView>(R.id.rvGallery) }
    private val mGalleryAdapter by lazy { GalleryAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        StatusBarCompat.compat(this)

        mRvGallery.apply {
            this.layoutManager = GridLayoutManager(this@GalleryActivity, 2)
            this.adapter = mGalleryAdapter

            this.addItemDecoration(object : RecyclerView.ItemDecoration() {

                override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                    super.onDrawOver(c, parent, state)
                }

                override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                    super.onDraw(c, parent, state)
                }

                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)

                    val layoutmanager = (parent.layoutManager as GridLayoutManager)
                    val spanCount: Int = layoutmanager.spanCount

                    val viewPosition: Int = layoutmanager.getPosition(view)

                    val rem = viewPosition.rem(spanCount)

                    when(rem){
                        0->{
                            outRect.right = dp2px(5f)
                        }
                        else->{
                            outRect.left = dp2px(5f)
                        }
                    }
                    outRect.bottom = dp2px(10f)
                }
            })
        }

        MediaHelp.Companion.Build(this).setDataCallback(this).build().startImages(true, contentResolver)
    }


    override fun mediaCallback(mediaList: MutableList<MediaBean>) {
        mGalleryAdapter.resetData(mediaList)
    }

}