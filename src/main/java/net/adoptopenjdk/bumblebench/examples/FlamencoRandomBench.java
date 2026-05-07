
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
 * FlamencoRandomBench score: 674990208.000000 (675.0M 2033.0%)
 *               uncertainty:   0.3%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * FlamencoRandomBench score: 672420416.000000 (672.4M 2032.6%)
 *               uncertainty:   0.6%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * FlamencoRandomBench score: 719216640.000000 (719.2M 2039.4%)
 *               uncertainty:   0.2%
 * <br>
 * GraalVM Java 24:
 * <br>
 * FlamencoRandomBench score: 683796416.000000 (683.8M 2034.3%)
 *               uncertainty:   0.4%
 */
public final class FlamencoRandomBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		FlamencoRandom rng = new FlamencoRandom(0x12345678);
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
