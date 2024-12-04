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
 * HotSpot Java 8:
 * <br>
 * SoloRandomBench score: 900940992.000000 (900.9M 2061.9%)
 *             uncertainty:   0.3%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * SoloRandomBench score: 1065462592.000000 (1.065G 2078.7%)
 *             uncertainty:   0.5%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * SoloRandomBench score: 1072571200.000000 (1.073G 2079.3%)
 *             uncertainty:   0.3%
 * <br>
 * GraalVM Java 22:
 * <br>
 * SoloRandomBench score: 1435337088.000000 (1.435G 2108.5%)
 *             uncertainty:   1.3%
 * <br>
 * HotSpot Java 23 (Adoptium):
 * <br>
 * SoloRandomBench score: 1065293760.000000 (1.065G 2078.7%)
 *             uncertainty:   0.7%
 */
public final class SoloRandomBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		SoloRandom rng = new SoloRandom(0x12345678);
		long sum = 0L;
		for (long i = 0; i < numIterations; i++)
			sum += rng.nextLong();
		return numIterations;
	}
}
