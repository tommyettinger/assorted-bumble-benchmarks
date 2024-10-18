
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
 * Taupe32RandomIntBench score: 598574784.000000 (598.6M 2021.0%)
 *                   uncertainty:   0.9%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * Taupe32RandomIntBench score: 658335168.000000 (658.3M 2030.5%)
 *                   uncertainty:   0.4%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * Taupe32RandomIntBench score: 737175552.000000 (737.2M 2041.8%)
 *                   uncertainty:   0.4%
 * <br>
 * GraalVM Java 22:
 * <br>
 * Taupe32RandomIntBench score: 578435392.000000 (578.4M 2017.6%)
 *                   uncertainty:   0.2%
 */
public final class Taupe32RandomIntBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		Taupe32Random rng = new Taupe32Random(0x12345678);
		int sum = 0;
		for (long i = 0; i < numIterations; i++)
			sum += rng.nextInt();
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
 * GraalVM Java 22:
 * <br>
 *
 */
