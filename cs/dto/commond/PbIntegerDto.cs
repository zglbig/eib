using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class PbIntegerDto : SerializeMessage {
	[ProtoMember(1)]
	public int value;
}
