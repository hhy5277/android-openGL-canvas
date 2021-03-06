/*
 *
 *  *
 *  *  * Copyright (C) 2016 ChillingVan
 *  *  *
 *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  * you may not use this file except in compliance with the License.
 *  *  * You may obtain a copy of the License at
 *  *  *
 *  *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *  *
 *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  * See the License for the specific language governing permissions and
 *  *  * limitations under the License.
 *  *
 *
 */

package com.chillingvan.canvasglsample.animation;

import android.content.Context;
import android.util.AttributeSet;

import com.chillingvan.canvasgl.ICanvasGL;
import com.chillingvan.canvasgl.glview.texture.GLContinuousTextureView;
import com.chillingvan.canvasglsample.animation.bubble.Bubble;
import com.chillingvan.canvasglsample.animation.bubble.MovableObj;
import com.chillingvan.canvasglsample.animation.bubble.Wall;

import java.util.ArrayList;
import java.util.List;

import static com.chillingvan.canvasglsample.animation.AnimGLView.INTERNVAL_TIME_MS;

/**
 * Created by Chilling on 2016/11/5.
 */

public class AnimGLTextureView extends GLContinuousTextureView {

    private List<Bubble> bubbles = new ArrayList<>();
    private Wall wallTop = new Wall.WallY(0);
    private Wall wallLeft = new Wall.WallX(0);
    private Wall wallBottom;
    private Wall wallRight;

    public AnimGLTextureView(Context context) {
        super(context);
    }

    public AnimGLTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimGLTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        wallBottom = new Wall.WallY(h);
        wallRight = new Wall.WallX(w);
    }


    public void setBubbles(List<Bubble> bubbles) {
        this.bubbles.addAll(bubbles);
    }

    @Override
    protected void onGLDraw(ICanvasGL canvas) {
        for (Bubble bubble : bubbles) {
            bubble.glDraw(canvas);
            if (wallTop.isTouch(bubble.point, bubble.collisionRadius) || wallBottom.isTouch(bubble.point, bubble.collisionRadius)) {
                bubble.onCollision(MovableObj.CollisionListener.DIRECTION_VERTICAL);
            } else if (wallLeft.isTouch(bubble.point, bubble.collisionRadius) || wallRight.isTouch(bubble.point, bubble.collisionRadius)) {
                bubble.onCollision(MovableObj.CollisionListener.DIRECTION_HORIZONTAL);
            }
            bubble.updatePosition(INTERNVAL_TIME_MS);
        }

    }
}
