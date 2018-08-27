using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class DiceCountDto : SerializeMessage {
	[ProtoMember(1)]
	public int one;
	[ProtoMember(2)]
	public int two;
	[ProtoMember(3)]
	public long battleCount;
}
