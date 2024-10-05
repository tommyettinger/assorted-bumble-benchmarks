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
 * Choo32IntRandomBench score: 837448128.000000 (837.4M 2054.6%)
 *                  uncertainty:   2.0%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * Choo32IntRandomBench score: 762159104.000000 (762.2M 2045.2%)
 *                  uncertainty:   0.3%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * Choo32IntRandomBench score: 1006462144.000000 (1.006G 2073.0%)
 *                  uncertainty:   0.6%
 * <br>
 * GraalVM Java 22:
 * <br>
 * Choo32IntRandomBench score: 918278272.000000 (918.3M 2063.8%)
 *                  uncertainty:   1.0%
 */
public final class Choo32IntRandomBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		Choo32Random rng = new Choo32Random(0x12345678);
		int sum = 0;
		for (long i = 0; i < numIterations; i++)
			sum += rng.nextInt();
		return numIterations;
	}
}
