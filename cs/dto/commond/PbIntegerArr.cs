using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class PbIntegerArr : SerializeMessage {
	[ProtoMember(1)]
	public List<int> vale;
}
