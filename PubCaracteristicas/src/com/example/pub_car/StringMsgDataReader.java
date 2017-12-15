package com.example.pub_car;
// CoreDX DDL Generated code.  Do not modify - modifications may be overwritten.
import com.toc.coredx.DDS.*;


public final class StringMsgDataReader extends DataReader  {
  
  StringMsgDataReader (Subscriber             s,
                        TopicDescription       td,
                        SWIGTYPE_p__DataReader j_dr) {
     super(s,td,j_dr);
  }
  public ReturnCode_t    read( StringMsgSeq                   received_data,
                        SampleInfoSeq           sample_infos,
			 int                     max_samples,
			 long                    sample_states,
			 long                    view_states,
			 long                    instance_states) {
    sample_infos.value  = null;
    SampleSeq tmp_seq   = new SampleSeq();
    ReturnCode_t retval =  _read(tmp_seq, sample_infos, max_samples,
                                 sample_states, view_states, instance_states,
                                 StringMsg.class);
    received_data.value = (StringMsg[])tmp_seq.value;
    return retval;
  }
  public ReturnCode_t    read_w_condition( StringMsgSeq       received_data,
                        SampleInfoSeq           sample_infos,
			 int                     max_samples,
			 ReadCondition           condition) {
    sample_infos.value  = null;
    SampleSeq tmp_seq   = new SampleSeq();
    ReturnCode_t retval =  _read_w_condition(tmp_seq, sample_infos, max_samples,
                                 condition, StringMsg.class);
    received_data.value = (StringMsg[])tmp_seq.value;
    return retval;
  }
  public ReturnCode_t    read_next_sample( StringMsg          received_data,
                                    SampleInfo  sample_info) {
    ReturnCode_t retval;
    StringMsgSeq tmp_seq = new StringMsgSeq();
    SampleInfoSeq si_seq = new SampleInfoSeq();
    retval = read(tmp_seq, si_seq, 1, coredx.DDS_NOT_READ_SAMPLE_STATE, 
                             coredx.DDS_ANY_VIEW_STATE,
                             coredx.DDS_ANY_INSTANCE_STATE);
    if (retval == ReturnCode_t.RETCODE_OK) {
      received_data.copy(tmp_seq.value[0]);
      sample_info.copy(si_seq.value[0]);
      retval = ReturnCode_t.RETCODE_OK;
      return_loan(tmp_seq, si_seq);
    }
    return retval;
  }
  public ReturnCode_t    read_instance( StringMsgSeq     received_data,
                        SampleInfoSeq           sample_infos,
			 int                     max_samples,
			 InstanceHandle_t        handle,
			 long                    sample_states,
			 long                    view_states,
			 long                    instance_states) {
    sample_infos.value  = null;
    SampleSeq tmp_seq   = new SampleSeq();
    ReturnCode_t retval =  _read_instance(tmp_seq, sample_infos, max_samples,
                                 handle, sample_states, view_states, instance_states,
                                 StringMsg.class);
    received_data.value = (StringMsg[])tmp_seq.value;
    return retval;
  }
  public ReturnCode_t    read_next_instance( StringMsgSeq     received_data,
                        SampleInfoSeq           sample_infos,
			 int                     max_samples,
			 InstanceHandle_t        prev_handle,
			 long                    sample_states,
			 long                    view_states,
			 long                    instance_states) {
    sample_infos.value  = null;
    SampleSeq tmp_seq   = new SampleSeq();
    ReturnCode_t retval =  _read_next_instance(tmp_seq, sample_infos, max_samples,
                                 prev_handle, sample_states, view_states, instance_states,
                                 StringMsg.class);
    received_data.value = (StringMsg[])tmp_seq.value;
    return retval;
  }
  public ReturnCode_t    read_next_instance_w_condition( StringMsgSeq     received_data,
                        SampleInfoSeq           sample_infos,
			 int                     max_samples,
			 InstanceHandle_t        handle,
			 ReadCondition           condition) {
    sample_infos.value  = null;
    SampleSeq tmp_seq   = new SampleSeq();
    ReturnCode_t retval =  _read_next_instance_w_condition(tmp_seq, sample_infos, max_samples,
                                 handle, condition, StringMsg.class);
    received_data.value = (StringMsg[])tmp_seq.value;
    return retval;
  }
  public ReturnCode_t    take( StringMsgSeq                   received_data,
                        SampleInfoSeq           sample_infos,
			 int                     max_samples,
			 long                    sample_states,
			 long                    view_states,
			 long                    instance_states) {
    sample_infos.value  = null;
    SampleSeq tmp_seq = new SampleSeq();
    ReturnCode_t retval =  _take(tmp_seq, sample_infos, max_samples,
                                 sample_states, view_states, instance_states,
                                 StringMsg.class);
    received_data.value = (StringMsg[])tmp_seq.value;
    return retval;
  }
  public ReturnCode_t    take_w_condition( StringMsgSeq       received_data,
                        SampleInfoSeq           sample_infos,
			 int                     max_samples,
			 ReadCondition           condition) {
    sample_infos.value  = null;
    SampleSeq tmp_seq   = new SampleSeq();
    ReturnCode_t retval =  _take_w_condition(tmp_seq, sample_infos, max_samples,
                                 condition, StringMsg.class);
    received_data.value = (StringMsg[])tmp_seq.value;
    return retval;
  }
  public ReturnCode_t    take_next_sample( StringMsg          received_data,
                                    SampleInfo  sample_info) {
    ReturnCode_t retval;
    StringMsgSeq tmp_seq = new StringMsgSeq();
    SampleInfoSeq si_seq = new SampleInfoSeq();
    retval = take(tmp_seq, si_seq, 1, coredx.DDS_NOT_READ_SAMPLE_STATE, 
                             coredx.DDS_ANY_VIEW_STATE,
                             coredx.DDS_ANY_INSTANCE_STATE);
    if (retval == ReturnCode_t.RETCODE_OK) {
      received_data.copy(tmp_seq.value[0]);
      sample_info.copy(si_seq.value[0]);
      retval = ReturnCode_t.RETCODE_OK;
      return_loan(tmp_seq, si_seq);
    }
    return retval;
  }
  public ReturnCode_t    take_instance( StringMsgSeq     received_data,
                        SampleInfoSeq           sample_infos,
			 int                     max_samples,
			 InstanceHandle_t        handle,
			 long                    sample_states,
			 long                    view_states,
			 long                    instance_states) {
    sample_infos.value  = null;
    SampleSeq tmp_seq   = new SampleSeq();
    ReturnCode_t retval =  _take_instance(tmp_seq, sample_infos, max_samples,
                                 handle, sample_states, view_states, instance_states,
                                 StringMsg.class);
    received_data.value = (StringMsg[])tmp_seq.value;
    return retval;
  }
  public ReturnCode_t    take_next_instance( StringMsgSeq     received_data,
                        SampleInfoSeq           sample_infos,
			 int                     max_samples,
			 InstanceHandle_t        prev_handle,
			 long                    sample_states,
			 long                    view_states,
			 long                    instance_states) {
    sample_infos.value  = null;
    SampleSeq tmp_seq   = new SampleSeq();
    ReturnCode_t retval =  _take_next_instance(tmp_seq, sample_infos, max_samples,
                                 prev_handle, sample_states, view_states, instance_states,
                                 StringMsg.class);
    received_data.value = (StringMsg[])tmp_seq.value;
    return retval;
  }
  public ReturnCode_t    take_next_instance_w_condition( StringMsgSeq     received_data,
                        SampleInfoSeq           sample_infos,
			 int                     max_samples,
			 InstanceHandle_t        handle,
			 ReadCondition           condition) {
    sample_infos.value  = null;
    SampleSeq tmp_seq   = new SampleSeq();
    ReturnCode_t retval =  _take_next_instance_w_condition(tmp_seq, sample_infos, max_samples,
                                 handle, condition, StringMsg.class);
    received_data.value = (StringMsg[])tmp_seq.value;
    return retval;
  }
  public ReturnCode_t    return_loan( StringMsgSeq                   received_data,
                                      SampleInfoSeq           sample_infos) {
    ReturnCode_t retval =  _return_loan(null, sample_infos);
    return retval;
  }
  public ReturnCode_t     get_key_value(StringMsg                  key_holder,
                                        InstanceHandle_t    handle) {
    ReturnCode_t retval  = _get_key_value(key_holder, handle);
    return retval;
  }
  public InstanceHandle_t lookup_instance(StringMsg                  instance_data) {
    InstanceHandle_t retval = _lookup_instance(instance_data);
    return retval;
  }
}; // StringMsg
