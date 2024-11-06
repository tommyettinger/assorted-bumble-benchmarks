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
import net.adoptopenjdk.bumblebench.core.MiniBench;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8:
 * <br>
 * KryoReadBench score: 791.959595 (792.0 667.5%)
 *           uncertainty:   2.0%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * KryoReadBench score: 787.097351 (787.1 666.8%)
 *           uncertainty:   1.7%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * KryoReadBench score: 766.513489 (766.5 664.2%)
 *           uncertainty:   3.9%
 * <br>
 * GraalVM Java 22:
 * <br>
 * KryoReadBench score: 869.969360 (870.0 676.8%)
 *           uncertainty:   3.5%
 * <br>
 * HotSpot Java 23 (Adoptium):
 * <br>
 * KryoReadBench score: 758.789246 (758.8 663.2%)
 *           uncertainty:   5.0%
 */
/**
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 23 (Adoptium):
 * <br>
 * KryoReadBench score: 826.265747 (826.3 671.7%)
 *           uncertainty:   0.9%
 */
public final class KryoReadBench extends MiniBench {
	@Override
	protected int maxIterationsPerLoop() {
		return 1000007;
	}

	@Override
	protected long doBatch(long numLoops, int numIterationsPerLoop) throws InterruptedException {
		HashMap<String, ArrayList<Vector2>> big;
		Kryo kryo = new Kryo();
		kryo.register(HashMap.class);
		kryo.register(ArrayList.class);
		kryo.register(Vector2.class);

		long counter = 0;

		byte[] bytes = new HeadlessFiles().local("kryo.dat").readBytes();
		ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
		buffer.put(bytes);
		buffer.flip();
		ByteBufferInput input = new ByteBufferInput(buffer);
		for (long i = 0; i < numLoops; i++) {
			for (int j = 0; j < numIterationsPerLoop; j++) {
				input.setBuffer(buffer);
				startTimer();
				big = kryo.readObject(input, HashMap.class);
				pauseTimer();
				input.reset();
				counter += big.size();
			}
		}
		return numLoops * numIterationsPerLoop;
	}
}

// OLD
/*
 * Java 17:
 * <br>
 * KryoReadBench score: 450.349823 (450.3 611.0%)
 *           uncertainty:   0.4%
 */
