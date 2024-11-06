/*******************************************************************************
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*******************************************************************************/

package net.adoptopenjdk.bumblebench.examples;

import com.badlogic.gdx.backends.headless.HeadlessFiles;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.github.tommyettinger.ds.ObjectList;
import com.github.tommyettinger.ds.ObjectObjectMap;
import net.adoptopenjdk.bumblebench.core.MiniBench;

import java.nio.ByteBuffer;

/*
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8:
 * <br>
 *
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 *
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 *
 * <br>
 * GraalVM Java 22:
 * <br>
 *
 * <br>
 * HotSpot Java 23 (Adoptium):
 * <br>
 *
 */

/**
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 23 (Adoptium):
 * <br>
 * KryoNotMoreReadBench score: 519.106506 (519.1 625.2%)
 *                  uncertainty:   0.5%
 */
public final class KryoNotMoreReadBench extends MiniBench {
	@Override
	protected int maxIterationsPerLoop() {
		return 1000007;
	}

	@Override
	protected long doBatch(long numLoops, int numIterationsPerLoop) throws InterruptedException {
		ObjectObjectMap<String, ObjectList<Vector2>> big;
		Kryo kryo = new Kryo();
		kryo.register(Vector2.class);
		kryo.register(ObjectList.class);
		kryo.register(ObjectObjectMap.class);

		long counter = 0;

		byte[] bytes = new HeadlessFiles().local("kryonotmore.dat").readBytes();
		ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
		buffer.put(bytes);
		buffer.flip();
		ByteBufferInput input = new ByteBufferInput(buffer);
		for (long i = 0; i < numLoops; i++) {
			for (int j = 0; j < numIterationsPerLoop; j++) {
				input.setBuffer(buffer);
				startTimer();
				big = kryo.readObject(input, ObjectObjectMap.class);
				pauseTimer();
				input.reset();
				counter += big.size();
			}
		}
		return numLoops * numIterationsPerLoop;
	}
}

// TEMPLATE
/*
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8:
 * <br>
 *
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 *
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 *
 * <br>
 * GraalVM Java 22:
 * <br>
 *
 * <br>
 * HotSpot Java 23 (Adoptium):
 * <br>
 *
 */


// OLD
/*
 * Windows 10, 10th gen i7 mobile hexacore at 2.6 GHz:
 * <br>
 * HotSpot Java 8:
 * <br>
 * KryoMoreReadBench score: 459.841248 (459.8 613.1%)
 *               uncertainty:   2.5%
 * <br>
 * OpenJ9 Java 15:
 * <br>
 * KryoMoreReadBench score: 307.733337 (307.7 572.9%)
 *               uncertainty:   0.2%
 * <br>
 * HotSpot Java 16 (AdoptOpenJDK):
 * <br>
 * KryoMoreReadBench score: 459.402710 (459.4 613.0%)
 *               uncertainty:   2.3%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * KryoMoreReadBench score: 435.844788 (435.8 607.7%)
 *               uncertainty:   0.2%
 * <br>
 * GraalVM Java 17:
 * <br>
 * KryoMoreReadBench score: 437.515961 (437.5 608.1%)
 *               uncertainty:   0.9
 * <br>
 * OpenJ9 Java 17 (Semeru):
 * <br>
 *
 * <br>
 * HotSpot Java 18 (Adoptium):
 * <br>
 * KryoMoreReadBench score: 435.724854 (435.7 607.7%)
 *               uncertainty:   2.6%
 * <br>
 * HotSpot Java 19 (BellSoft):
 * <br>
 * KryoMoreReadBench score: 416.608795 (416.6 603.2%)
 *               uncertainty:   2.7%
 */
