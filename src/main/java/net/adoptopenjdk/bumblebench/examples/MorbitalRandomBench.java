
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

import net.adoptopenjdk.bumblebench.core.MicroBench;

/**
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
 * <br>
 * MorbitalRandomBench score: 939519488.000000 (939.5M 2066.1%)
 *                 uncertainty:   0.9%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * MorbitalRandomBench score: 1112695936.000000 (1.113G 2083.0%)
 *                 uncertainty:   0.5%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * MorbitalRandomBench score: 1110083584.000000 (1.110G 2082.8%)
 *                 uncertainty:   0.4%
 * <br>
 * GraalVM Java 24:
 * <br>
 * MorbitalRandomBench score: 1214828672.000000 (1.215G 2091.8%)
 *                 uncertainty:   1.3%
 */
public final class MorbitalRandomBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		MorbitalRandom rng = new MorbitalRandom(0x12345678);
		long sum = 0L;
		for (long i = 0; i < numIterations; i++)
			sum += rng.nextLong();
		return numIterations;
	}
}

// TEMPLATE
/* *
 * Windows 11, 12th Gen i7-12800H at 2.40 GHz:
 * <br>
 * HotSpot Java 8 (BellSoft):
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
 * GraalVM Java 24:
 * <br>
 *
 */
