using System;
using System.Collections.Generic;
using ProtoBuf;
[ProtoContract]
public class HallRedEnvelopePlayerListDto : SerializeMessage {
	[ProtoMember(1)]
	public List<HallRedEnvelopePlayerDto> redList;
}
