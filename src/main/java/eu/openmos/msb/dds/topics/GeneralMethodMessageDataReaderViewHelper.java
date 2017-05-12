package eu.openmos.msb.dds.topics;

import org.opensplice.dds.dcps.Utilities;

public final class GeneralMethodMessageDataReaderViewHelper
{

    public static eu.openmos.msb.dds.topics.GeneralMethodMessageDataReaderView narrow(java.lang.Object obj)
    {
        if (obj == null) {
            return null;
        } else if (obj instanceof eu.openmos.msb.dds.topics.GeneralMethodMessageDataReaderView) {
            return (eu.openmos.msb.dds.topics.GeneralMethodMessageDataReaderView)obj;
        } else {
            throw Utilities.createException(Utilities.EXCEPTION_TYPE_BAD_PARAM, null);
        }
    }

    public static eu.openmos.msb.dds.topics.GeneralMethodMessageDataReaderView unchecked_narrow(java.lang.Object obj)
    {
        if (obj == null) {
            return null;
        } else if (obj instanceof eu.openmos.msb.dds.topics.GeneralMethodMessageDataReaderView) {
            return (eu.openmos.msb.dds.topics.GeneralMethodMessageDataReaderView)obj;
        } else {
            throw Utilities.createException(Utilities.EXCEPTION_TYPE_BAD_PARAM, null);
        }
    }

}