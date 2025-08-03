/*
 * Copyright (c) 2022-2024 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package net.adoptopenjdk.bumblebench.examples;

import com.github.yellowstonegames.grid.Point4Float;
import org.apache.fory.Fory;
import org.apache.fory.memory.MemoryBuffer;
import org.apache.fory.serializer.Serializer;

/**
 * Fory {@link Serializer} for libGDX {@link Point4Float}s.
 */
public class Point4FloatSerializer extends Serializer<Point4Float> {
    public Point4FloatSerializer(Fory fory) {
        super(fory, Point4Float.class);
    }

    @Override
    public void write(final MemoryBuffer output, final Point4Float data) {
        output.writeFloat32(data.x);
        output.writeFloat32(data.y);
        output.writeFloat32(data.z);
        output.writeFloat32(data.w);
    }

    @Override
    public Point4Float read(MemoryBuffer input) {
        return new Point4Float(input.readFloat32(), input.readFloat32(), input.readFloat32(), input.readFloat32());
    }
}
