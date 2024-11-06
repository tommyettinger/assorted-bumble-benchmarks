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
import com.github.tommyettinger.ds.ObjectDeque;
import com.github.tommyettinger.ds.ObjectList;
import com.github.tommyettinger.ds.ObjectObjectMap;
import com.github.tommyettinger.ds.ObjectObjectOrderedMap;
import com.github.tommyettinger.tantrum.jdkgdxds.ObjectDequeSerializer;
import com.github.tommyettinger.tantrum.jdkgdxds.ObjectListSerializer;
import com.github.tommyettinger.tantrum.jdkgdxds.ObjectObjectMapSerializer;
import com.github.tommyettinger.tantrum.jdkgdxds.ObjectObjectOrderedMapSerializer;
import com.github.tommyettinger.tantrum.libgdx.Vector2Serializer;
import net.adoptopenjdk.bumblebench.core.MiniBench;
import org.apache.fury.Fury;
import org.apache.fury.config.Language;
import org.apache.fury.logging.LoggerFactory;

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
 * FuryMoreOrderedReadBench score: 918.961731 (919.0 682.3%)
 *                      uncertainty:   1.1%
 */
public final class FuryMoreOrderedReadBench extends MiniBench {
	@Override
	protected int maxIterationsPerLoop() {
		return 1000007;
	}

	@Override
	protected long doBatch(long numLoops, int numIterationsPerLoop) throws InterruptedException {
		byte[] data = new HeadlessFiles().local("furymoreordered.dat").readBytes();
		ObjectObjectOrderedMap<String, ObjectDeque<Vector2>> big;
		LoggerFactory.disableLogging();
		Fury fury = Fury.builder().withLanguage(Language.JAVA).build();
		fury.registerSerializer(ObjectObjectOrderedMap.class, new ObjectObjectOrderedMapSerializer(fury));
		fury.registerSerializer(ObjectDeque.class, new ObjectDequeSerializer(fury));
		fury.registerSerializer(Vector2.class, new Vector2Serializer(fury));

		long counter = 0;
		for (long i = 0; i < numLoops; i++) {
			for (int j = 0; j < numIterationsPerLoop; j++) {
				startTimer();
				big = fury.deserializeJavaObject(data, ObjectObjectOrderedMap.class);
				counter += big.size();
				pauseTimer();
			}
		}
		return numLoops * numIterationsPerLoop;
	}
}

