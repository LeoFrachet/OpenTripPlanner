package org.opentripplanner.updater.siri.updater.light;

import java.net.URI;
import java.time.Duration;
import org.opentripplanner.updater.siri.updater.SiriSXUpdater;
import org.opentripplanner.updater.spi.HttpHeaders;

public record SiriSXLightUpdaterParameters(
  String configRef,
  String feedId,
  URI uri,
  Duration frequency,
  Duration earlyStart,
  Duration timeout,
  HttpHeaders requestHeaders
)
  implements SiriSXUpdater.SiriSXUpdaterParameters {
  @Override
  public String requestorRef() {
    return "OpenTripPlanner";
  }

  @Override
  public boolean blockReadinessUntilInitialized() {
    return false;
  }

  @Override
  public String url() {
    return uri.toString();
  }
}
