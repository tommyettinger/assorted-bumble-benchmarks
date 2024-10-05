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
 * Choo32LongRandomBench score: 424087296.000000 (424.1M 1986.5%)
 *                   uncertainty:   0.7%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * Choo32LongRandomBench score: 369206496.000000 (369.2M 1972.7%)
 *                   uncertainty:   0.4%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * Choo32LongRandomBench score: 480176032.000000 (480.2M 1999.0%)
 *                   uncertainty:   0.3%
 * <br>
 * GraalVM Java 22:
 * <br>
 * Choo32LongRandomBench score: 424928320.000000 (424.9M 1986.7%)
 *                   uncertainty:   0.2%
 */
public final class Choo32LongRandomBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		Choo32Random rng = new Choo32Random(0x12345678);
		long sum = 0;
		for (long i = 0; i < numIterations; i++)
			sum += rng.nextLong();
		return numIterations;
	}
}
