
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
 * Gnome32RandomLongBench score: 148524080.000000 (148.5M 1881.6%)
 *                    uncertainty:   1.8%
 * <br>
 * HotSpot Java 17 (Adoptium):
 * <br>
 * Gnome32RandomLongBench score: 191107536.000000 (191.1M 1906.8%)
 *                    uncertainty:   1.3%
 * <br>
 * HotSpot Java 21 (BellSoft):
 * <br>
 * Gnome32RandomLongBench score: 207792960.000000 (207.8M 1915.2%)
 *                    uncertainty:   0.6%
 * <br>
 * GraalVM Java 22:
 * <br>
 * Gnome32RandomLongBench score: 171174176.000000 (171.2M 1895.8%)
 *                    uncertainty:   0.4%
 */
public final class Gnome32RandomLongBench extends MicroBench {

	protected long doBatch(long numIterations) throws InterruptedException {
		Gnome32Random rng = new Gnome32Random(0x12345678);
		long sum = 0L;
		for (long i = 0; i < numIterations; i++)
			sum += rng.nextLong();
		return numIterations;
	}
}
